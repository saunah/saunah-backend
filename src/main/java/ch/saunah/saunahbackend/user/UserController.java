package ch.saunah.saunahbackend.user;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SignUpResponse signUp(@RequestBody SignUpBody signUpBody) throws Exception {
        User createdUser = userService.signUp(signUpBody);
        mailService.sendMail(createdUser);
        return new SignUpResponse("success");
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody SignInBody signInBody) throws Exception {
        return userService.signIn(signInBody);
    }

    @GetMapping(value = "/signup/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SignUpResponse verify(@PathVariable Integer id) throws Exception {
        boolean status = userService.verifyUser(id);
        if (status) {
            return new SignUpResponse("User verified");
        }
        return new SignUpResponse("User not found");
    }

}
