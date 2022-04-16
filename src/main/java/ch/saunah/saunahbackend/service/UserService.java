package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.security.JwtTokenUtil;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * This class contains registration, verification and login methods.
 */
@Service
public class UserService {

    private static final String PWD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * This method registers a new user to the database.
     *
     * @param signUpBody contains all user credentials
     * @return createdUser
     * @throws Exception
     */
    public User signUp(SignUpBody signUpBody) throws Exception {
        User user = userRepository.findByEmail(signUpBody.getEmail());
        if (user != null) {
            throw new Exception("Email already taken");
        }

        if (!Pattern.matches(EMAIL_PATTERN, signUpBody.getEmail())) {
            throw new Exception("The email is not valid");
        }

        if (!Pattern.matches(PWD_PATTERN, signUpBody.getPassword())) {
            throw new Exception("Password does not require the conditions");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(signUpBody.getPassword());
        user = new User();
        user.setEmail(signUpBody.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setFirstName(signUpBody.getFirstName());
        user.setLastName(signUpBody.getLastName());
        user.setPhoneNumber(signUpBody.getPhoneNumber());
        user.setPlz(signUpBody.getPlz());
        user.setPlace(signUpBody.getPlace());
        user.setStreet(signUpBody.getStreet());
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    /**
     * This method checks if the user's provided id matches the existing one in the database.
     *
     * @param id userid
     * @return true if he provided id matches, false if it does not
     */
    public boolean verifyUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /**
     * This method logs in a registered user.
     *
     * @param signInBody contains login relevant user credentials
     * @return if the login was successful
     * @throws Exception
     */
    public ResponseEntity<JwtResponse> signIn(SignInBody signInBody) throws Exception {
        try {
            User foundUser = userRepository.findByEmail(signInBody.getEmail());
            if(foundUser == null){
                throw new Exception("No user found with this Email!");
            }
            if(!foundUser.isActivated()){
                throw new Exception("User has not been activated");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInBody.getEmail(), signInBody.getPassword()));
            if (authentication == null){
                throw new Exception("Authentication was not successful");
            }
            else{
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        String jwtToken = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(signInBody.getEmail()));

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }
}
