package ch.saunah.saunahbackend;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private UserRepository userRepository;

    @Operation(description = "Returns an object containing greeting information.")
    @GetMapping(value = {"greeting/{name}"})
    public Greeting greeting(@PathVariable(value = "name", required = true) String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


    @Operation(description = "Returns a list of all users.")
    @GetMapping(path="users")
    public @ResponseBody Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @Operation(description = "Returns the user with the ID specified.")
    @GetMapping(path="user/{id}")
    public @ResponseBody User getUser(@PathVariable(value = "id", required = true) Integer id) {
        // This returns a JSON or XML with the users
        return userRepository.findById(id).get();
    }

    @Operation(description = "Allows adding a new user.")
    @PostMapping(path="user/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam("name") String name, @RequestParam("emailAddress") String emailAddress) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User user = new User(name, emailAddress);
        userRepository.save(user);
        return String.format("User saved with id %s",  user.getId());
    }
}
