package ch.saunah.saunahbackend;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRepository;

@RestController
@Controller
public class GreetingController {
    private static final String template = "Goodbye, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"greeting/{name}"})
    public Greeting greeting(@PathVariable(value = "name", required = true) String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping(path="user/{id}")
    public @ResponseBody User getUser(@PathVariable(value = "id", required = true) Integer id) {
        // This returns a JSON or XML with the users
        return userRepository.findById(id).get();
    }

    @GetMapping(path="users")
    public @ResponseBody Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path="user/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam("name") String name, @RequestParam("emailAddress") String emailAddress) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User user = new User(name, emailAddress);
        userRepository.save(user);
        return String.format("User saved with id %s",  user.getId());
    }
}
