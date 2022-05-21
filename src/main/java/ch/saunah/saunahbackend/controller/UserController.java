package ch.saunah.saunahbackend.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.BadAttributeValueExpException;
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
import ch.saunah.saunahbackend.dto.SaunahApiResponse;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.UserBody;
import ch.saunah.saunahbackend.dto.UserResponse;
import ch.saunah.saunahbackend.exception.SaunahLoginException;
import ch.saunah.saunahbackend.exception.SaunahMailException;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controls the different operations of a user account.
 */
@RestController
@RequestMapping
public class UserController {
    private static final String RESPONSE_SUCCESS = "success";

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Operation(description = "Registers an account and sends a verification mail to the specified mail.")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unable to send Mail", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class)))
    })
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody UserBody userBody) throws SaunahMailException {
        User createdUser = userService.signUp(userBody);
        mailService.sendUserActivationMail(createdUser.getEmail(), createdUser.getActivationId());
        return ResponseEntity.ok(RESPONSE_SUCCESS);
    }

    @Operation(description = "Logs the user in and returns the JWT token of the user.")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login was successful", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "401", description = "No valid credentials", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    @ResponseBody
    public ResponseEntity<JwtResponse> login(@RequestBody SignInBody signInBody) throws SaunahLoginException {
        return userService.signIn(signInBody);
    }

    @Operation(description = "Activates the account for the user with the specified verificationId.")
    @GetMapping(value = "/verify/{verificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User account was successfully verified"),
        @ApiResponse(responseCode = "400", description = "Verification not valid", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    @ResponseBody
    public ResponseEntity<String> verify(@PathVariable String verificationId) {
        userService.verifyUser(verificationId);
        return ResponseEntity.ok("Account activated");
    }

    @Operation(description = "Send a reset Password Mail to the user")
    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "New password set"),
        @ApiResponse(responseCode = "500", description = "Unable to send Mail", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class)))
    })
    @ResponseBody
    public ResponseEntity<String> resetPasswordRequest(@RequestBody ResetPasswordRequestBody resetPasswordRequestBody) throws SaunahMailException {
        User user = userService.getUserByMail(resetPasswordRequestBody.getEmail());
        String passwordToken = userService.createResetPasswordToken(user);
        mailService.sendPasswordResetMail(resetPasswordRequestBody.getEmail(), passwordToken);
        return ResponseEntity.ok(RESPONSE_SUCCESS);
    }

    @Operation(description = "Reset the users password with the new one")
    @PutMapping(value = "/reset-password/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request, new Password does not match the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    @ResponseBody
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @PathVariable String token) throws BadAttributeValueExpException {
        userService.resetPassword(token, resetPasswordBody);
        return ResponseEntity.ok(RESPONSE_SUCCESS);
    }

    @Operation(description = "Returns a list of all Users.")
    @GetMapping(path = "users/all")
    public @ResponseBody
    List<UserResponse> getAllUsers() {
        return userService.getAllUser().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Operation(description = "Returns a list of all Users that have not been soft deleted.")
    @GetMapping(path = "users")
    public @ResponseBody
    List<UserResponse> getAllVisibleUsers() {
        return userService.getAllVisibleUser().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Operation(description = "Returns the user with the ID specified.")
    @GetMapping(path="users/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the user", content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "User not found", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody ResponseEntity<UserResponse> getUser(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        User currentUser = userService.getUserByMail(principal.getName());
        if (currentUser.getRole().equals(UserRole.ADMIN) || currentUser.getId().equals(id)) {
            return ResponseEntity.ok(new UserResponse(userService.getUser(id)));
        }
        throw new AuthenticationException("user is not authenticated to view other users than himself");
    }

    @Operation(description = "update the user information.")
    @PutMapping(path="users/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User edited", content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody ResponseEntity<UserResponse> editUser(@PathVariable(value = "id", required = true) Integer id, Principal principal, @RequestBody UserBody userBody) throws AuthenticationException {
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
