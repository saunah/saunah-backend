package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SaunaController {
    private final AtomicLong counter = new AtomicLong();

    // TODO change to SaunaTypeRepository
    @Autowired
    private UserRepository userRepository;

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "user/add") // Map ONLY POST Requests
    public @ResponseBody
    String addSaunaType(@RequestParam("name") String name,
                        @RequestParam("description") String description,
                        @RequestParam("picture") boolean picture,
                        @RequestParam("type") boolean isMobile,
                        @RequestParam("preis") int preis,
                        @RequestParam("maxTemp") int maxTemp,
                        @RequestParam("numberOfPeople") int numberOfPeople,
                        @RequestParam("location") String location,
                        @RequestParam("street") String street,
                        @RequestParam("plz") int plz) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Sauna saunaType = new Sauna(name, description, picture, isMobile, preis, maxTemp, numberOfPeople,
            location, street, plz) {
            //userRepository.save(saunaType);
        };
        return String.format("The sauna was saved with id %s",saunaType.getId());
    }

    // TODO Implement??
    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "user/add") // Map ONLY POST Requests
    public @ResponseBody
    String removeSaunaType(@RequestParam("Id") int id) {
        return String.format("The sauna was with id %s has been removed", 1);
    }

    // TODO Implement??
    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "user/add") // Map ONLY POST Requests
    public @ResponseBody
    String updateSaunaType(@RequestParam("Id") int id) {
        return String.format("The sauna was with id %s has been removed", 1);
    }

}



