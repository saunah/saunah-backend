package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.user.SignInBody;
import ch.saunah.saunahbackend.user.SignInResponse;
import ch.saunah.saunahbackend.user.SignUpBody;
import ch.saunah.saunahbackend.user.SignUpResponse;
import ch.saunah.saunahbackend.user.UserReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    public SignUpResponse signUp(SignUpBody signUpBody) throws Exception {
        User user = userRepository.findByEmail(signUpBody.getEmail());
        if (user != null)
        {
            throw new Exception("Email already taken");
        }

        if (signUpBody.getPassword().length() < 8){
            throw new Exception("Password length must be atleast ");
        }

        if (!Pattern.matches(PASSWORD_PATTERN, signUpBody.getPassword())){
            throw new Exception("Password does not require the conditions");
        }

        user = new User();
        user.setEmail(signUpBody.getEmail());
        user.setPasswordHash(signUpBody.getPassword());
        user.setFirstName(signUpBody.getFirstName());
        user.setLastName(signUpBody.getLastName());
        user.setPhoneNumber(signUpBody.getPhoneNumber());
        user.setPlz(signUpBody.getPlz());
        user.setPlace(signUpBody.getPlace());
        user.setStreet(signUpBody.getStreet());
        userRepository.save(user);

        return new SignUpResponse("success");
    }


    //ToDo: Token creation
    public SignInResponse signIn(SignInBody signInBody){
        try {
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInBody.getEmail(), signInBody.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (Exception e){
            return new SignInResponse(UserReturnCode.Unsuccessful, "");
        }
        return new SignInResponse(UserReturnCode.Successful, "");
    }
}
