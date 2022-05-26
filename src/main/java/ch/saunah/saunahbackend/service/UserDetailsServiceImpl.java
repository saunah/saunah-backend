package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * This class contains user login data
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * This method returns the user by username from the database.
     *
     * @param email the email of the user
     * @return the user with his email password and his authorizations
     * @throws UsernameNotFoundException when the user cant be found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ch.saunah.saunahbackend.model.User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return new User(user.getEmail(), user.getPasswordHash(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
