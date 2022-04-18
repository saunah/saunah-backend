package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Operation(description = "Registers an account and sends a verification mail to the specified mail.")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody SignUpBody signUpBody) throws Exception {
        User createdUser = userService.signUp(signUpBody);
        mailService.sendUserActivationMail(createdUser.getEmail(), createdUser.getActivationId());
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Logs the user in and returns the JWT token of the user.")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JwtResponse> login(@RequestBody SignInBody signInBody) throws Exception {
        return userService.signIn(signInBody);
    }

    @Operation(description = "Activates the account for the user with the specified verificationId.")
    @GetMapping(value = "/verify/{verificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> verify(@PathVariable String verificationId) throws Exception {
        boolean status = userService.verifyUser(verificationId);
        if (status) {
            return ResponseEntity.ok("Verification successful");
        }
        return ResponseEntity.ok("Account activated");
    }

}
