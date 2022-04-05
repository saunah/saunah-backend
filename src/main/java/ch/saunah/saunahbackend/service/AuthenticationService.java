package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.repository.AuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationTokenRepository tokenRepository;

    public void saveToken(int userId){

    }
}
