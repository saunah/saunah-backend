package ch.saunah.saunahbackend.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.management.BadAttributeValueExpException;
import javax.validation.ValidationException;

import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.dto.ResetPasswordBody;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.UserBody;
import ch.saunah.saunahbackend.exception.SaunahLoginException;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.security.JwtResponse;
import ch.saunah.saunahbackend.security.JwtTokenUtil;


/**
 * This class contains registration, verification and login methods.
 */
@Service
public class UserService {

    private static final String PWD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()%[{}]:;',?/*~$^+=<>._|`-]).{8,20}$";
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final Integer VALID_PERIOD = 3600 * 1000;

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
     * @param userBody contains all user credentials
     * @return createdUser
     * @throws IllegalArgumentException when conditions don't match
     */
    public User signUp(UserBody userBody) throws IllegalArgumentException {
        User user = userRepository.findByEmail(userBody.getEmail());
        if (user != null) {
            throw new IllegalArgumentException("Email already taken");
        }

        if (!Pattern.matches(EMAIL_PATTERN, userBody.getEmail())) {
            throw new IllegalArgumentException("The email is not valid");
        }

        if (!Pattern.matches(PWD_PATTERN, userBody.getPassword())) {
            throw new IllegalArgumentException("Password does not require the conditions");
        }

        String hashedPassword = passwordEncoder.encode(userBody.getPassword());
        user = new User();
        user.setEmail(userBody.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setFirstName(userBody.getFirstName());
        user.setLastName(userBody.getLastName());
        user.setPhoneNumber(userBody.getPhoneNumber());
        user.setZip(userBody.getZip());
        user.setPlace(userBody.getPlace());
        user.setStreet(userBody.getStreet());
        user.setActivationId(UUID.randomUUID().toString());
        user.setIsDeleted(false);
        user.setInitialAdmin(false);
        if (hasUsers()) {
            user.setRole(UserRole.USER);
        } else {
            user.setRole(UserRole.ADMIN);
            user.setInitialAdmin(true);
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
     * This method returns the user with matching token
     */
    public User getUserByResetPasswordToken(String token) {
        List<User> user = getAllUser().stream().filter(
            x -> passwordEncoder.matches(token , x.getResetPasswordHash())
        ).collect(Collectors.toList());

        if (user.size() != 1) {
            throw new IllegalStateException("Password-Reset-Token is not unique.");
        }

        return user.get(0);
    }

    /**
     * Create a cryptographically secure token to authenticate the user requesting
     * a password reset and saves it on the user
     *
     * @param user The user that requested the password change
     * @return a cryptographically secure token
     */
    public String createResetPasswordToken(User user) {
        String token = UUID.randomUUID().toString();

        String hashedPassword = passwordEncoder.encode(token);

        user.setResetPasswordHash(hashedPassword);
        user.setTokenValidDate(new Date(System.currentTimeMillis() + VALID_PERIOD));
        userRepository.save(user);
        return token;
    }

    /**
     * Reset the users password if all conditions are met.
     *
     * @param token            token to identify the user
     * @param resetPasswordBody new Password
     * @throws IllegalArgumentException when conditions don't match
     * @throws BadAttributeValueExpException when the user didn't request a new password
     */
    public void resetPassword (String token, ResetPasswordBody resetPasswordBody) throws IllegalArgumentException, BadAttributeValueExpException {
        User user = getUserByResetPasswordToken(token);
        if (user == null) {
            throw new NotFoundException("There is no User found matching the token:" + token);
        }
        if (user.getResetPasswordHash().isBlank()){
            throw new BadAttributeValueExpException("This user didn't request a new password");
        }
        Date now = new Date(System.currentTimeMillis());
        if (new Date(user.getTokenValidDate().getTime()).before(now)){
            throw new ValidationException("The Token is expired");
        }
        if (!passwordEncoder.matches(token, user.getResetPasswordHash())){
            throw new BadCredentialsException("The Token doesn't match");
        }

        if (!Pattern.matches(PWD_PATTERN, resetPasswordBody.getNewPassword())) {
            throw new ValidationException("Password does not require the conditions");
        }
        user.setResetPasswordHash("");
        user.setPasswordHash(passwordEncoder.encode(resetPasswordBody.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * This method checks if the user's provided id matches the existing one in the database.
     *
     * @param activationId userid
     */
    public void verifyUser(String activationId) throws NotFoundException{
        User user = userRepository.findByActivationId(activationId);
        if (user == null) {
            throw new NotFoundException(String.format("User with the activation id %s not found!", activationId));
        }
        user.setActivated(true);
        userRepository.save(user);
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
     * @throws SaunahLoginException when login was not successful
     */
    public ResponseEntity<JwtResponse> signIn(SignInBody signInBody) throws SaunahLoginException {
        try {
            User foundUser = userRepository.findByEmail(signInBody.getEmail());
            if (foundUser == null) {
                throw new NotFoundException("No user found with this Email!");
            }
            if (!foundUser.isActivated()) {
                throw new IllegalAccessException("User has not been activated");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInBody.getEmail(), signInBody.getPassword()));
            if (authentication == null) {
                throw new AuthenticationException("Authentication was not successful");
            } else {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
            }
        } catch (Exception e) {
           throw new SaunahLoginException(e.getMessage());
        }
        String jwtToken = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(signInBody.getEmail()));
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    /**
     * Get the user according to the provided ID
     *
     * @param id the id of the user to find
     * @return The required user
     * @throws NotFoundException if no such user exists
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
     * Return a list of user that have not been "deleted"
     *
     * @return list of users
     */
    public List<User> getAllVisibleUser() {
        return userRepository.findByIsDeleted(false);
    }


    /**
     * Edit an already existing User
     * @param id the id of the user to be edited
     * @param userBody the parameters to be changed
     * @return the edited user
     */
    public User editUser(int id, UserBody userBody) {
        User editUser = getUser(id);
        setUserFields(editUser, userBody);
        return userRepository.save(editUser);
    }

    private User setUserFields(User user, UserBody userBody) {
        user.setFirstName(userBody.getFirstName());
        user.setLastName(userBody.getLastName());
        user.setPhoneNumber(userBody.getPhoneNumber());
        user.setPlace(userBody.getPlace());
        user.setZip(userBody.getZip());
        user.setStreet(userBody.getStreet());

        if(userBody.getRole() != null) {
            if (UserRole.USER.equals(userBody.getRole()) &&
                UserRole.ADMIN.equals(user.getRole())){
                if (getAllUser().stream().anyMatch(x -> user.getId() != x.getId() && UserRole.ADMIN.equals(x.getRole()))) {
                    user.setRole(userBody.getRole());
                }
            }
            else {
                user.setRole(userBody.getRole());
            }
        }
        return user;
    }

    /**
     * Delete a User
     * @param id the id of the user that should be deleted
     * @return true, if the user can be deleted
     */
    public User deleteUser(int id) {
        User deleteUser = getUser(id);
        if(!deleteUser.getInitialAdmin()) {
            deleteUser.setIsDeleted(true);
        }
        return userRepository.save(deleteUser);
    }
}
