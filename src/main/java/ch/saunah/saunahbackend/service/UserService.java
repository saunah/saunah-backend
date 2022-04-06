package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.user.SignInBody;
import ch.saunah.saunahbackend.user.SignInResponse;
import ch.saunah.saunahbackend.user.UserReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    public SignInResponse signIn(SignInBody signInBody){
        try {
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInBody.getEmail(), signInBody.getPasswordHash()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (Exception e){
            return new SignInResponse(UserReturnCode.UnsuccessfullLogin, "");
        }
        return new SignInResponse(UserReturnCode.SuccessfulLogin, "");
    }
}
