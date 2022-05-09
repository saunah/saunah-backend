package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.*;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the different operations of a user account.
 */
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
        userService.verifyUser(verificationId);
        return ResponseEntity.ok("Account activated");
    }

    @Operation(description = "Send a reset Password Mail to the user")
    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> resetPasswordRequest(@RequestBody ResetPasswordBody resetPasswordBody) throws Exception {
        User user = userService.getUserByMail(resetPasswordBody.getEmail());
        int passwordToken = userService.createResetPasswordtoken(user);
        mailService.sendPasswordResetMail(resetPasswordBody.getEmail(), user.getId(), passwordToken);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Reset the users password with the new one")
    @PostMapping(value = "/reset-password/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @PathVariable Integer userID) throws Exception {
        try {
            userService.resetPassword(userID, resetPasswordBody);
        } catch (IndexOutOfBoundsException exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Returns a list of all Users.")
    @GetMapping(path="users")
    public @ResponseBody
    List<UserResponse> getAllUsers() {
        return userService.getAllUser().stream().map(x -> new UserResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns the user with the ID specified.")
    @GetMapping(path="users/{id}")
    public @ResponseBody ResponseEntity<UserResponse> getUser(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        User currentUser = userService.getUserByMail(principal.getName());
        if (currentUser.getRole().equals(UserRole.ADMIN) || currentUser.getId() == id) {
            return ResponseEntity.ok(new UserResponse(userService.getUser(id)));
        }
        throw new AuthenticationException("user is not authenticated to view other users than himself");
    }

    @Operation(description = "Returns the user with the ID specified.")
    @PutMapping(path="users/userRole/{id}")
    public @ResponseBody ResponseEntity<UserResponse> changeUserRole(@PathVariable(value = "id", required = true) Integer id,  Principal principal) throws AuthenticationException {
        User currentUser = userService.getUserByMail(principal.getName());
        if (currentUser.getRole().equals(UserRole.ADMIN)) {
            User changedUser = userService.getUser(id);
            if (changedUser.getRole().equals(UserRole.ADMIN)) {
                return ResponseEntity.ok(new UserResponse(userService.editUserRole(changedUser, UserRole.USER)));
            } else {
                return ResponseEntity.ok(new UserResponse(userService.editUserRole(changedUser, UserRole.ADMIN)));
            }
        }
        throw new AuthenticationException("user is not authenticated to change the userRole");
    }

    @Operation(description = "Returns the user with the ID specified.")
    @PutMapping(path="users/{id}")
    public @ResponseBody ResponseEntity<UserResponse> editUser(@PathVariable(value = "id", required = true) Integer id, Principal principal, @RequestBody SignUpBody signUpBody) throws AuthenticationException {
        User currentUser = userService.getUserByMail(principal.getName());
        if (currentUser.getRole().equals(UserRole.ADMIN) || currentUser.getId() == id) {
            return ResponseEntity.ok(new UserResponse(userService.editUser(id, signUpBody)));
        }
        throw new AuthenticationException("user is not authenticated to edit other users");
    }

    @Operation(description = "Returns the current logged in User.")
    @GetMapping(path="users/whoami")
    public @ResponseBody ResponseEntity<UserResponse> whoami(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws Exception {
        return ResponseEntity.ok(new UserResponse(userService.whoami()));
    }
}
