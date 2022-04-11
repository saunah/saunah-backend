package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SaunaController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private SaunaRepository saunaRepository;

    @Operation(description = "Allows adding a new Sauna type.")
    @PostMapping(path = "sauna/add") // Map ONLY POST Requests
    public @ResponseBody
    String createSauna(@RequestParam("name") String name,
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
        long id = counter.incrementAndGet();
        Sauna sauna = new Sauna(id, name, description, picture, isMobile, preis, maxTemp, numberOfPeople,
            location, street, plz);
        saunaRepository.save(sauna);
        return String.format("The sauna was saved with id %s",sauna.getId());
    }

    @Operation(description = "Returns a list of saunas.")
    @GetMapping(path="saunas")
    public @ResponseBody Iterable<Sauna> getAllSauna() {
        return saunaRepository.findAll();
    }

    @Operation(description = "Returns the sauna with the ID specified.")
    @GetMapping(path="sauna/{id}")
    public @ResponseBody Sauna getSauna(@PathVariable(value = "id", required = true) Integer id) {
        // This returns a JSON or XML with the users
        return saunaRepository.findById(id).get();
    }

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
    String editSauna(@RequestParam("Id") int id) {
        saunaRepository.save(getSauna(id));
        return String.format("The sauna was with id %s has been removed", 1);
    }
}



