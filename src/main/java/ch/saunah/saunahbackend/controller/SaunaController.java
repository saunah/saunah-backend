package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.service.SaunaService;
import ch.saunah.saunahbackend.service.UserService;
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

    /**
     * create a new Sauna
     * @param saunaTypeBody Contains the required parameters
     * @return ResponseEntity after success
     * @throws Exception Throws an Exception
     */
    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "sauna/add") // Map ONLY POST Requests
    public @ResponseBody
    ResponseEntity<String> createSauna(@RequestBody SaunaTypeBody saunaTypeBody) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Sauna createdSauna = saunaService.addSauna(saunaTypeBody);
        return ResponseEntity.ok("success");
    }

    /**
     * Get all sauna that are registered in the Database
     * @return All information from all saunas
     */
    @Operation(description = "Returns a list of saunas.")
    @GetMapping(path="saunas")
    public @ResponseBody ResponseEntity<String> getAllSauna() {
        saunaService.getAllSauna();
        return ResponseEntity.ok("success");
    }

    /**
     * Return Information of a certain sauna
     * @param id The Id of the sauna that is requested
     * @return All Information from a specified sauna
     */
    @Operation(description = "Returns the sauna with the ID specified.")
    @GetMapping(path="sauna/{id}")
    public @ResponseBody ResponseEntity<String> getSauna(@PathVariable(value = "id", required = true) Integer id) {
        saunaService.getSauna(id);
        return ResponseEntity.ok("success");
    }

    /**
     * Delete a certain sauna according to the given id
     * @param id The Id of the sauna to be removed from the database
     * @return Message if sauna has been successfully removed
     */
    @Operation(description = "Allows removing a existing Sauna with the ID specified.")
    @PostMapping(path = "sauna/remove") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> removeSauna(@RequestParam("Id") int id) {
        saunaService.removeSauna(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "sauna/edit") // Map ONLY POST Requests
    public @ResponseBody
    String editSauna(@RequestParam("Id") int id,
                     @RequestParam("description") String description,
                     @RequestParam("picture") boolean picture,
                     @RequestParam("type") boolean isMobile,
                     @RequestParam("prize") int prize,
                     @RequestParam("maxTemp") int maxTemp,
                     @RequestParam("numberOfPeople") int numberOfPeople,
                     @RequestParam("location") String location,
                     @RequestParam("street") String street,
                     @RequestParam("plz") int plz
    ) {
        Sauna editSauna = saunaService.getSauna(id);
        editSauna.setDescription(description);
        editSauna.setPicture(picture);
        editSauna.setType(isMobile);
        editSauna.setPrize(prize);
        editSauna.setMaxTemp(maxTemp);
        editSauna.setNumberOfPeople(numberOfPeople);
        editSauna.setLocation(location);
        editSauna.setStreet(street);
        editSauna.setPlz(plz);
        saunaRepository.save(editSauna);
        return String.format("The sauna was with id %s has been edited", 1);
    }
}



