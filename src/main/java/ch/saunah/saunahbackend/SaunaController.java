package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    /**
     * Create a new sauna
     * @param name The name of the sauna
     * @param description A short description of the sauna
     * @param picture A set of picture of the sauna
     * @param isMobile If the sauna is mobile
     * @param prize The cost of said sauna
     * @param maxTemp The maximal temperature of the sauna
     * @param numberOfPeople The number of people who have space in the sauna
     * @param location Where the sauna is located (city)
     * @param street The street where the sauna is stationed
     * @param plz The PLZ number of the place where the sauna is stationed
     * @return Message when the sauna has been successfully added
     */
    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "sauna/add") // Map ONLY POST Requests
    public @ResponseBody
    boolean createSauna(@RequestParam("name") String name,
                       @RequestParam("description") String description,
                       @RequestParam("picture") boolean picture,
                       @RequestParam("type") boolean isMobile,
                       @RequestParam("prize") int prize,
                       @RequestParam("maxTemp") int maxTemp,
                       @RequestParam("numberOfPeople") int numberOfPeople,
                       @RequestParam("location") String location,
                       @RequestParam("street") String street,
                       @RequestParam("plz") int plz) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        long id = counter.incrementAndGet();
        Sauna sauna = new Sauna(id, name, description, picture, isMobile, prize, maxTemp, numberOfPeople,
            location, street, plz);

        saunaRepository.save(sauna);

        return true;
    }

    /**
     * Get all sauna that are registered in the Database
     * @return All information from all saunas
     */
    @Operation(description = "Returns a list of saunas.")
    @GetMapping(path="saunas")
    public @ResponseBody Iterable<Sauna> getAllSauna() {
        return saunaRepository.findAll();
    }

    /**
     * Return Information of a certain sauna
     * @param id The Id of the sauna that is requested
     * @return All Information from a specified sauna
     */
    @Operation(description = "Returns the sauna with the ID specified.")
    @GetMapping(path="sauna/{id}")
    public @ResponseBody Sauna getSauna(@PathVariable(value = "id", required = true) Integer id) {
        // This returns a JSON or XML with the users
        return saunaRepository.findById(id).get();
    }

    /**
     * Delete a certain sauna according to the given id
     * @param id The Id of the sauna to be removed from the database
     * @return Message if sauna has been successfully removed
     */
    @Operation(description = "Allows removing a existing Sauna with the ID specified.")
    @PostMapping(path = "sauna/remove") // Map ONLY POST Requests
    public @ResponseBody
    String removeSauna(@RequestParam("Id") int id) {
        saunaRepository.delete(getSauna(id));
        return String.format("The sauna was with id %s has been removed", 1);
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
        Sauna editSauna = getSauna(id);
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



