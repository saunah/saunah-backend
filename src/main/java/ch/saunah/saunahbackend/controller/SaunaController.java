package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.service.SaunaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Controls the different operation that can be done with sauna types
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SaunaController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private SaunaRepository saunaRepository;

    @Autowired
    private SaunaService saunaService;

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "sauna/add")
    public @ResponseBody
    ResponseEntity<String> createSauna(@RequestBody SaunaTypeBody saunaTypeBody) {
        Sauna createdSauna = saunaService.addSauna(saunaTypeBody);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Returns a list of saunas.")
    @GetMapping(path="saunas")
    public @ResponseBody Iterable<Sauna> getAllSauna() {
        return saunaService.getAllSauna();
    }

    @Operation(description = "Returns the sauna with the ID specified.")
    @GetMapping(path="sauna/{id}")
    public @ResponseBody Sauna getSauna(@PathVariable(value = "id", required = true) Integer id) {
        return saunaService.getSauna(id);
    }

    @Operation(description = "Allows removing a existing Sauna with the ID specified.")
    @PostMapping(path = "sauna/remove")
    public @ResponseBody ResponseEntity<String> removeSauna(@RequestParam("Id") int id) {
        saunaService.removeSauna(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows editing an existing Sauna type.")
    @PostMapping(path = "sauna/edit")
    public @ResponseBody
    ResponseEntity<String> editSauna(@RequestParam("Id") int id, @RequestBody SaunaTypeBody saunaTypeBody) {
        saunaService.editSauna(id, saunaTypeBody);
        return ResponseEntity.ok("success");
    }
}



