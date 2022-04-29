package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.dto.PriceResponse;
import ch.saunah.saunahbackend.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controls the different operations that can be done with price
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Operation(description = "Allows adding a new Price structure.")
    @PostMapping(path = "price/add")
    public @ResponseBody
    ResponseEntity<PriceResponse> createPrice(@RequestBody PriceBody priceBody) {
        return ResponseEntity.ok(new PriceResponse(priceService.addPrice(priceBody)));
    }

    @Operation(description = "Allows removing a existing Price structure with the ID specified.")
    @PostMapping(path = "price/remove")
    public @ResponseBody ResponseEntity<String> removePrice(@RequestParam("Id") int id) {
        priceService.removePrice(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows editing an existing Price structure.")
    @PostMapping(path = "price/edit")
    public @ResponseBody
    ResponseEntity<PriceResponse> editPrice(@RequestParam("Id") int id, @RequestBody PriceBody priceBody) {
        return ResponseEntity.ok(new PriceResponse(priceService.editPrice(id, priceBody)));
    }
}
