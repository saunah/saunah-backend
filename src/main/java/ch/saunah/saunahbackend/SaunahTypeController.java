package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.SaunaType;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SaunahTypeController {
    private final AtomicLong counter = new AtomicLong();

    // TODO change to SaunaTypeRepository
    @Autowired
    private UserRepository userRepository;

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "user/add") // Map ONLY POST Requests
    public @ResponseBody
    String addSaunaType(@RequestParam("sauna_name") String saunaName,
                        @RequestParam("sauna_description") String saunaDescription,
                        @RequestParam("sauna_Type") boolean isMobile,
                        @RequestParam("sauna_Location") String saunaLocation) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        SaunaType saunaType = new SaunaType(saunaName, saunaDescription, isMobile,saunaLocation);
        //userRepository.save(saunaType);
        return String.format("The sauna was saved with id %s", saunaType.getSaunaId());
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



