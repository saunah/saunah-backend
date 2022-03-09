package ch.saunah.saunahbackend;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = {"greeting/{name}"})
    public Greeting greeting(@PathVariable(value = "name", required = true) String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
