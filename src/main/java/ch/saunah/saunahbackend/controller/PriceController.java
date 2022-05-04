package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.dto.PriceResponse;
import ch.saunah.saunahbackend.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the different operations that can be done with price
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Operation(description = "Allows adding a new Price structure.")
    @PostMapping(path = "prices")
    public @ResponseBody
    ResponseEntity<PriceResponse> createPrice(@RequestBody PriceBody priceBody) {
        return ResponseEntity.ok(new PriceResponse(priceService.addPrice(priceBody)));
    }

    @Operation(description = "Allows removing a existing Price structure with the ID specified.")
    @DeleteMapping(path = "prices/{id}")
    public @ResponseBody ResponseEntity<String> removePrice(@PathVariable("id") int id) {
        priceService.removePrice(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows editing an existing Price structure.")
    @PutMapping(path = "prices/{id}")
    public @ResponseBody
    ResponseEntity<PriceResponse> editPrice(@PathVariable("id") int id, @RequestBody PriceBody priceBody) {
        return ResponseEntity.ok(new PriceResponse(priceService.editPrice(id, priceBody)));
    }

    @Operation(description = "Returns a list of Price structures.")
    @GetMapping(path="prices")
    public @ResponseBody
    List<PriceResponse> getAllPrice() {
        return priceService.getAllPrice().stream().map(x -> new PriceResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns the price with the ID specified.")
    @GetMapping(path="prices/{id}")
    public @ResponseBody ResponseEntity<PriceResponse> getPrice(@PathVariable(value = "id", required = true) Integer id) {
        return ResponseEntity.ok(new PriceResponse(priceService.getPrice(id)));
    }
}
