package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.*;
import ch.saunah.saunahbackend.dto.ResetPasswordBody;
import ch.saunah.saunahbackend.dto.ResetPasswordRequestBody;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.UserBody;
import ch.saunah.saunahbackend.dto.SaunahApiResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unable to send Mail", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class)))
    })
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody UserBody userBody) throws SaunahMailException {
        User createdUser = userService.signUp(userBody);
        mailService.sendUserActivationMail(createdUser.getEmail(), createdUser.getActivationId());
        return ResponseEntity.ok("success");
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
    public ResponseEntity<String> verify(@PathVariable String verificationId){
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
        int passwordToken = userService.createResetPasswordtoken(user);
        mailService.sendPasswordResetMail(resetPasswordRequestBody.getEmail(), user.getId(), passwordToken);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Reset the users password with the new one")
    @PostMapping(value = "/reset-password/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request, new Password does not match the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    @ResponseBody
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @PathVariable Integer userID) throws Exception {
        userService.resetPassword(userID, resetPasswordBody);
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
    @GetMapping(path="users/whoami")
    public @ResponseBody ResponseEntity<UserResponse> whoami(Principal principal) {
        return ResponseEntity.ok(new UserResponse(userService.getUserByMail(principal.getName())));
    }
}
