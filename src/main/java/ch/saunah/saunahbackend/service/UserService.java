package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.user.SignInBody;
import ch.saunah.saunahbackend.user.SignInResponse;
import ch.saunah.saunahbackend.user.UserReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public SignInResponse signIn(SignInBody signInBody){
        User user = userRepository.findByEmail(signInBody.getEmail());
        if (user  == null){
            throw new NotFoundException("user not found");
        }

        if (!signInBody.getPasswordHash().equals(user.getPassword_hash())){
            throw new NotFoundException("wrong password");
        }

        return new SignInResponse(UserReturnCode.SuccessfulLogin, "132412341234");
    }
}
