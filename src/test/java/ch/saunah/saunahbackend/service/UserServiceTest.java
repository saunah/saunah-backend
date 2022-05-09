package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.dto.ResetPasswordBody;
import org.webjars.NotFoundException;

import java.security.Principal;

/**
 * This class tests all user service methods.
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private SignUpBody signUpBody = null;
    private SignInBody signInBody = null;

    @BeforeEach
    void setUp() {
        signUpBody = new SignUpBody();
        signUpBody.setFirstName("Hans");
        signUpBody.setLastName("Muster");
        signUpBody.setPlace("Winterthur");
        signUpBody.setEmail("hans.muster@mustermail.ch");
        signUpBody.setPhoneNumber("0123");
        signUpBody.setStreet("Teststrasse 123");
        signUpBody.setPassword("ZH_a?!WD32");
        signUpBody.setPlz("1324");
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * This test checks if the user won't be able to signup when an email is already in use.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUpThrowCheck() throws Exception {
        userService.signUp(signUpBody); //create first one
        Exception exception = assertThrows(Exception.class, () -> {
            userService.signUp(signUpBody);
        });
        String expectedMessage = "Email already taken";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * This test checks if the user won't be able to signup with an invalid email.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUpEmailCheck() throws Exception {
        signUpBody.setEmail("bademail");
        assertThrows(Exception.class, () -> userService.signUp(signUpBody));
        signUpBody.setEmail("goodemail@hotmail.com");
        assertDoesNotThrow(() -> userService.signUp(signUpBody));
    }

    /**
     * This test checks if the user won't be able to signup with an invalid password.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUpPasswordCheck() throws Exception {
        signUpBody.setPassword("badpassword");
        assertThrows(Exception.class, () -> userService.signUp(signUpBody));
        signUpBody.setPassword("Badpassword");
        assertThrows(Exception.class, () -> userService.signUp(signUpBody));
        signUpBody.setPassword("BadPassword1");
        assertThrows(Exception.class, () -> userService.signUp(signUpBody));
        signUpBody.setPassword("Bpw1#");
        assertThrows(Exception.class, () -> userService.signUp(signUpBody));
        signUpBody.setPassword("GoodPassword#1");
        assertDoesNotThrow(() -> userService.signUp(signUpBody));
    }

    /**
     * This test checks if the user successfully registered an account.
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUp() throws Exception {
        assertNull(userRepository.findByEmail(signUpBody.getEmail()));
        userService.signUp(signUpBody);
        User user = userRepository.findByEmail(signUpBody.getEmail());
        assertNotNull(user);
        assertEquals(signUpBody.getEmail(), user.getEmail());
        assertEquals(user.getRole(), UserRole.ADMIN);
        signUpBody.setEmail("lorem.ipsum@mustermail.ch");
        user = userService.signUp(signUpBody);
        assertEquals(user.getRole(), UserRole.USER);
    }

    /**
     * This test checks if the user can be verified with the assigned id.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void verifyUser() throws Exception {
        String wrongId = "100";
        userService.signUp(signUpBody);
        String userId = userRepository.findByEmail(signUpBody.getEmail()).getActivationId();
        boolean returnValueFound = userService.verifyUser(userId);
        boolean returnValueNull = userService.verifyUser(wrongId);
        assertTrue(returnValueFound);
        assertFalse(returnValueNull);
    }

    /**
     * Test if a user can sign in with the right and with the wrong credentials
     *
     * @throws Exception throws an exception when the login does not work with the right credentials
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signIn() throws Exception {
        userService.signUp(signUpBody);
        User user = userRepository.findByEmail("hans.muster@mustermail.ch");
        user.setActivated(true);
        userRepository.save(user);
        SignInBody signInBody = new SignInBody();
        signInBody.setEmail("hans.muster@mustermail.ch");
        signInBody.setPassword("ZH_a?!WD32");
        var response = userService.signIn(signInBody);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        signInBody.setEmail("notexisting mail");
        assertThrows(Exception.class, () -> userService.signIn(signInBody));
        signInBody.setEmail("hans.muster@mustermail.ch");
        user.setActivated(false);
        userRepository.save(user);
        assertThrows(Exception.class, () -> userService.signIn(signInBody));
        user.setActivated(true);
        userRepository.save(user);
        signInBody.setPassword("wrong password");
        assertThrows(Exception.class, () -> userService.signIn(signInBody));
        signInBody.setEmail("both wrong");
        assertThrows(Exception.class, () -> userService.signIn(signInBody));
    }
    /**
     * Test if a user can sign in with the right and with the wrong credentials
     *
     * @throws Exception throws an exception when the login does not work with the right credentials
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void passwordreset() throws Exception {
        userService.signUp(signUpBody);
        User user = userRepository.findByEmail("hans.muster@mustermail.ch");
        user.setActivated(true);
        userRepository.save(user);
        Integer resetPasswordToken = userService.createResetPasswordtoken(user);

        ResetPasswordBody resetPasswordBody = new ResetPasswordBody("hansmuster@mail.ch", "badToken" ,"newPassword12!");
        assertThrows(Exception.class, () -> userService.resetPassword(user.getId(),resetPasswordBody));
        ResetPasswordBody resetPasswordBody1 = new ResetPasswordBody("hansmuster@mail.ch", Integer.toString(resetPasswordToken) ,"badPassword");
        assertThrows(Exception.class, () -> userService.resetPassword(user.getId(),resetPasswordBody1));
        ResetPasswordBody resetPasswordBody2 = new ResetPasswordBody("hansmuster@mail.ch", Integer.toString(resetPasswordToken) ,"newPassword12!");
        assertDoesNotThrow(() -> userService.resetPassword(user.getId(),resetPasswordBody2));
    }

    /**
     * This test, checks if a user can be found via it's id
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getUser() throws Exception {
        assertThrows(NotFoundException.class, () -> userService.getUser(1));
        userService.signUp(signUpBody);
        assertNotNull(userService.getUser(1));
    }

    /**
     * This test checks if all saunas can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllUser() throws Exception {
        userService.signUp(signUpBody);
        signUpBody.setEmail("test1@gmail.com");
        userService.signUp(signUpBody);
        signUpBody.setEmail("test2@gmail.com");
        userService.signUp(signUpBody);
        assertEquals(3,userRepository.count());
        signUpBody.setEmail("test3@gmail.com");
        userService.signUp(signUpBody);
        assertEquals(4,userRepository.count());
    }

    /**
     * This test checks if the fields values of an existing user can be edited
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editUser() throws Exception {
        User user = userService.signUp(signUpBody);
        SignUpBody signUpBodyChanged = new SignUpBody();
        signUpBodyChanged.setFirstName("Maya");
        signUpBodyChanged.setLastName("Meier");
        signUpBodyChanged.setStreet("Inkastrasse");
        signUpBodyChanged.setPlace("Azteken");
        signUpBodyChanged.setPlz("666");
        signUpBodyChanged.setPhoneNumber("077 777 66 77");
        user = userService.editUser(user.getId(), signUpBodyChanged);
        checkUserFields(signUpBodyChanged, user);
    }

    /**
     * This method helps checking if the values are correct
     * @param signUpBody The user parameters
     * @param user the instance of a user
     */
    private void checkUserFields(SignUpBody signUpBody, User user) {
        assertEquals(signUpBody.getFirstName(), user.getFirstName());
        assertEquals(signUpBody.getLastName(), user.getLastName());
        assertEquals(signUpBody.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(signUpBody.getPlace(), user.getPlace());
        assertEquals(signUpBody.getPlz(), user.getPlz());
        assertEquals(signUpBody.getStreet(), user.getStreet());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editUserRole() throws Exception {
        User userAdmin = userService.signUp(signUpBody);
        signUpBody.setEmail("test1@gmail.com");
        User userNotAdmin = userService.signUp(signUpBody);
        assertEquals(UserRole.ADMIN,userAdmin.getRole());
        assertEquals(UserRole.USER,userNotAdmin.getRole());

        userService.editUserRole(userAdmin, UserRole.USER);
        userService.editUserRole(userNotAdmin, UserRole.ADMIN);
        assertEquals(UserRole.USER,userAdmin.getRole());
        assertEquals(UserRole.ADMIN,userNotAdmin.getRole());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void whoami() throws Exception {
        assertThrows(Exception.class, () -> userService.whoami());
        User user = userService.signUp(signUpBody);
        userService.verifyUser(user.getActivationId());
        signInBody = new SignInBody();
        signInBody.setEmail("hans.muster@mustermail.ch");
        signInBody.setEmail(signUpBody.getEmail());
        signInBody.setPassword(signUpBody.getPassword());
        userService.signIn(signInBody);
        //assertEquals(user, userService.whoami());
    }

}
