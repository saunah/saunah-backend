package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.ResetPasswordBody;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.security.JwtTokenUtil;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


import javax.management.BadAttributeValueExpException;
import javax.validation.ValidationException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * This class contains registration, verification and login methods.
 */
@Service
public class UserService {

    private static final String PWD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()%[{}]:;',?/*~$^+=<>._|`-]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        String hashedPassword = passwordEncoder.encode(signUpBody.getPassword());
        user = new User();
        user.setEmail(signUpBody.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setFirstName(signUpBody.getFirstName());
        user.setLastName(signUpBody.getLastName());
        user.setPhoneNumber(signUpBody.getPhoneNumber());
        user.setPlz(signUpBody.getPlz());
        user.setPlace(signUpBody.getPlace());
        user.setStreet(signUpBody.getStreet());
        user.setActivationId(UUID.randomUUID().toString());
        if (hasUsers()) {
            user.setRole(UserRole.USER);
        } else {
            user.setRole(UserRole.ADMIN);
        }
        return userRepository.save(user);
    }

    /**
     * This method returns the user with the mail
     */
    public User getUserByMail(String mail) {
        return userRepository.findByEmail(mail);

    }

    /**
     * Create a crypto secure token to authenicate the password requester and saves it on the user
     *
     * @param user The user that requested the password change
     * @return an int with 5 digits
     */
    public int createResetPasswordtoken(User user) {
        int min = 10000;
        int max = 99999;

        //Creates a random number between min and max
        SecureRandom random = new SecureRandom();
        int resetToken = Math.abs(random.nextInt() *(max-min+1)+min);
        String resetTokenValue = Integer.toString(resetToken);
        String hashedPassword = passwordEncoder.encode(resetTokenValue);
        user.setResetpasswordHash(hashedPassword);
        userRepository.save(user);
        return resetToken;
    }

    /**
     * Reset the users password if all conditons are met.
     *
     * @param userID            Int to identify
     * @param resetPasswordBody
     * @throws Exception
     */
    public void resetPassword (Integer userID , ResetPasswordBody resetPasswordBody) throws Exception{
        Optional<User> optionalUser = userRepository.findById(userID);
        if(optionalUser.isEmpty()) {
            throw new IndexOutOfBoundsException("There is no User with the ID:" + userID);
        }
        User user = optionalUser.get();
        if(user.getResetpasswordHash().isBlank()){
            throw new BadAttributeValueExpException("This user didn't request a new password");
        }
        if(!passwordEncoder.matches(resetPasswordBody.getResetToken(), user.getResetpasswordHash())){
            throw new BadCredentialsException("The Token doesn't match");
        }

        if (!Pattern.matches(PWD_PATTERN, resetPasswordBody.getNewPassword())) {
            throw new ValidationException("Password does not require the conditions");
        }
        user.setResetpasswordHash("");
        user.setPasswordHash(passwordEncoder.encode(resetPasswordBody.getNewPassword()));
        userRepository.save(user);

    }

    /**
     * This method checks if the user's provided id matches the existing one in the database.
     *
     * @param activationId userid
     * @return true if he provided id matches, false if it does not
     */
    public boolean verifyUser(String activationId) {
        User user = userRepository.findByActivationId(activationId);
        if (user != null) {
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /**
     * This method checks the Repository if a user exists
     *
     * @return true if a user is found
     */
    public boolean hasUsers() {
        return userRepository.findAll().iterator().hasNext();
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
            if (foundUser == null) {
                throw new Exception("No user found with this Email!");
            }
            if (!foundUser.isActivated()) {
                throw new Exception("User has not been activated");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInBody.getEmail(), signInBody.getPassword()));
            if (authentication == null) {
                throw new Exception("Authentication was not successful");
            } else {
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

    /**
     * Get the user according to the provided ID
     *
     * @param id the id of the user to find
     * @return The required user
     * @throws NotFoundException
     */
    public User getUser(int id) throws NotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new NotFoundException(String.format("User with id %d not found!", id));
        }
        return user;
    }

    /**
     * Return a list of all Users
     *
     * @return list of users
     */
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Edit an already existing User
     * @param id the id of the user to be edited
     * @param signUpBody the parameters to be changed
     * @return
     */
    public User editUser(int id, SignUpBody signUpBody) {
        User editUser = getUser(id);
        setUserFields(editUser, signUpBody);
        return userRepository.save(editUser);
    }

    private User setUserFields(User user, SignUpBody signUpBody) {
        user.setFirstName(signUpBody.getFirstName());
        user.setLastName(signUpBody.getLastName());
        user.setPhoneNumber(signUpBody.getPhoneNumber());
        user.setPlace(signUpBody.getPlace());
        user.setPlz(signUpBody.getPlz());;
        user.setStreet(signUpBody.getStreet());
        return user;
    }



}
