package ch.saunah.saunahbackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.saunah.saunahbackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Operation(description = "Allows adding a new user.")
    @PostMapping(path="user/signIn")
    public SignInResponse login(@RequestBody SignInBody signInBody) {


        //Login -> Response SignInResponse with newly created token...
        // Navigate to required Login -> GetRequest with Token -> Return AuthenticationAvailableResponse

        return new SignInResponse(UserReturnCode.UnsuccessfullLogin,"");
    }
}
