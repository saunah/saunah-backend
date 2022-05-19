package ch.saunah.saunahbackend.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.saunah.saunahbackend.dto.ResetPasswordBody;
import ch.saunah.saunahbackend.dto.ResetPasswordRequestBody;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.UserBody;
import ch.saunah.saunahbackend.dto.UserResponse;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

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
    public ResponseEntity<String> signUp(@RequestBody UserBody userBody) throws Exception {
        User createdUser = userService.signUp(userBody);
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
    public ResponseEntity<String> verify(@PathVariable String verificationId) {
        userService.verifyUser(verificationId);
        return ResponseEntity.ok("Account activated");
    }

    @Operation(description = "Send a reset Password Mail to the user")
    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> resetPasswordRequest(@RequestBody ResetPasswordRequestBody resetPasswordRequestBody) {
        User user = userService.getUserByMail(resetPasswordRequestBody.getEmail());
        String passwordToken = userService.createResetPasswordtoken(user);
        mailService.sendPasswordResetMail(resetPasswordRequestBody.getEmail(), passwordToken);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Reset the users password with the new one")
    @PostMapping(value = "/reset-password/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @PathVariable String token) throws Exception {
        userService.resetPassword(token,resetPasswordBody);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Returns a list of all Users.")
    @GetMapping(path = "users/all")
    public @ResponseBody
    List<UserResponse> getAllUsers() {
        return userService.getAllUser().stream().map(x -> new UserResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns a list of all Users that have not been soft deleted.")
    @GetMapping(path = "users")
    public @ResponseBody
    List<UserResponse> getAllVisibleUsers() {
        return userService.getAllVisibleUser().stream().map(x -> new UserResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns the user with the ID specified.")
    @GetMapping(path = "users/{id}")
    public @ResponseBody
    ResponseEntity<UserResponse> getUser(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        User currentUser = userService.getUserByMail(principal.getName());
        if (currentUser.getRole().equals(UserRole.ADMIN) || currentUser.getId().equals(id)) {
            return ResponseEntity.ok(new UserResponse(userService.getUser(id)));
        }
        throw new AuthenticationException("user is not authenticated to view other users than himself");
    }

    @Operation(description = "update the user information.")
    @PutMapping(path = "users/{id}")
    public @ResponseBody
    ResponseEntity<UserResponse> editUser(@PathVariable(value = "id", required = true) Integer id, Principal principal, @RequestBody UserBody userBody) throws AuthenticationException {
        User currentUser = userService.getUserByMail(principal.getName());
        if (currentUser.getRole().equals(UserRole.ADMIN) || currentUser.getId().equals(id)) {
            sanitizeUserBodyForRole(currentUser.getRole(), userBody);
            return ResponseEntity.ok(new UserResponse(userService.editUser(id, userBody)));
        }
        throw new AuthenticationException("user is not authenticated to edit other users");
    }

    private void sanitizeUserBodyForRole(UserRole role, UserBody userBody) {
        if (!role.equals(UserRole.ADMIN)) {
            userBody.setRole(null);
        }
    }

    @Operation(description = "Returns the current logged in User.")
    @GetMapping(path = "users/whoami")
    public @ResponseBody
    ResponseEntity<UserResponse> whoami(Principal principal) {
        return ResponseEntity.ok(new UserResponse(userService.getUserByMail(principal.getName())));
    }

    @Operation(description = "Soft deletes a User.")
    @DeleteMapping(path = "users/{id}")
    public @ResponseBody
    ResponseEntity<UserResponse> deleteUser(@PathVariable(value = "id", required = true) Integer id) {
        return ResponseEntity.ok(new UserResponse(userService.deleteUser(id)));
    }
}
