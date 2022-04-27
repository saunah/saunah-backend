package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.dto.PriceResponse;
import ch.saunah.saunahbackend.dto.SaunaResponse;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceService priceService;

    @Operation(description = "Allows adding a new Price structure.")
    @PostMapping(path = "price/add")
    public @ResponseBody
    ResponseEntity<PriceResponse> createPrice(@RequestBody PriceBody priceBody) {
        return ResponseEntity.ok(new PriceResponse(priceService.addPrice(priceBody)));
    }

   // TODO getPrice function => watch saunaController


    @Operation(description = "Allows removing a existing Price structure with the ID specified.")
    @PostMapping(path = "price/remove")
    public @ResponseBody ResponseEntity<String> removePrice(@RequestParam("Id") int id) {
        priceService.removePrice(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows editing an existing Price structure.")
    @PostMapping(path = "price/edit")
    public @ResponseBody
    ResponseEntity<String> editPrice(@RequestParam("Id") int id, @RequestBody PriceBody priceBody) {
        priceService.editPrice(id, priceBody);
        return ResponseEntity.ok("success");
    }



}
