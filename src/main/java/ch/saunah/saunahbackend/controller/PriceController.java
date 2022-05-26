package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.dto.PriceResponse;
import ch.saunah.saunahbackend.dto.SaunahApiResponse;
import ch.saunah.saunahbackend.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Price created"),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<PriceResponse> createPrice(@RequestBody PriceBody priceBody) throws NullPointerException {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new PriceResponse(priceService.addPrice(priceBody)));
    }

    @Operation(description = "Allows removing a existing Price structure with the ID specified.")
    @DeleteMapping(path = "prices/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Price deleted"),
        @ApiResponse(responseCode = "400", description = "Price does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody ResponseEntity<String> removePrice(@PathVariable("id") int id) throws NotFoundException {
        priceService.removePrice(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows editing an existing Price structure.")
    @PutMapping(path = "prices/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Price edited"),
        @ApiResponse(responseCode = "400", description = "Price does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<PriceResponse> editPrice(@PathVariable("id") int id, @RequestBody PriceBody priceBody) throws NotFoundException {
        return ResponseEntity.ok(new PriceResponse(priceService.editPrice(id, priceBody)));
    }

    @Operation(description = "Returns a list of Price structures.")
    @GetMapping(path="prices")
    public @ResponseBody
    List<PriceResponse> getAllPrice() {
        return priceService.getAllPrice().stream().map(PriceResponse::new).collect(Collectors.toList());
    }

    @Operation(description = "Returns the price with the ID specified.")
    @GetMapping(path="prices/{id}")
    public @ResponseBody ResponseEntity<PriceResponse> getPrice(@PathVariable(value = "id") Integer id) throws NotFoundException {
        return ResponseEntity.ok(new PriceResponse(priceService.getPrice(id)));
    }
}
