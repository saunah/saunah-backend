package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests all user service methods.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaunahBackendApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private SignUpBody signUpBody = null;

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
}
