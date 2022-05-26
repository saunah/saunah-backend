package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.ResetPasswordBody;
import ch.saunah.saunahbackend.dto.SignInBody;
import ch.saunah.saunahbackend.dto.UserBody;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;

/**
 * This class tests all user service methods.
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private UserBody userBody = null;

    @BeforeEach
    void setUp() {
        userBody = new UserBody();
        userBody.setFirstName("Hans");
        userBody.setLastName("Muster");
        userBody.setPlace("Winterthur");
        userBody.setEmail("hans.muster@mustermail.ch");
        userBody.setPhoneNumber("0123");
        userBody.setStreet("Teststrasse 123");
        userBody.setPassword("ZH_a?!WD32");
        userBody.setZip("1324");
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * This test checks if the user won't be able to signup when an email is already in use.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUpThrowCheck() {
        userService.signUp(userBody);
        Exception exception = assertThrows(Exception.class, () -> userService.signUp(userBody));
        String expectedMessage = "Email already taken";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * This test checks if the user won't be able to signup with an invalid email.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUpEmailCheck() {
        userBody.setEmail("bademail");
        assertThrows(Exception.class, () -> userService.signUp(userBody));
        userBody.setEmail("goodemail@hotmail.com");
        assertDoesNotThrow(() -> userService.signUp(userBody));
    }

    /**
     * This test checks if the user won't be able to signup with an invalid password.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUpPasswordCheck() {
        userBody.setPassword("badpassword");
        assertThrows(Exception.class, () -> userService.signUp(userBody));
        userBody.setPassword("Badpassword");
        assertThrows(Exception.class, () -> userService.signUp(userBody));
        userBody.setPassword("BadPassword1");
        assertThrows(Exception.class, () -> userService.signUp(userBody));
        userBody.setPassword("Bpw1#");
        assertThrows(Exception.class, () -> userService.signUp(userBody));
        userBody.setPassword("GoodPassword#1");
        assertDoesNotThrow(() -> userService.signUp(userBody));
    }

    /**
     * This test checks if the user successfully registered an account.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signUp() {
        assertNull(userRepository.findByEmail(userBody.getEmail()));
        userService.signUp(userBody);
        User user = userRepository.findByEmail(userBody.getEmail());
        assertNotNull(user);
        assertEquals(userBody.getEmail(), user.getEmail());
        assertEquals(UserRole.ADMIN, user.getRole());
        userBody.setEmail("lorem.ipsum@mustermail.ch");
        user = userService.signUp(userBody);
        assertEquals(UserRole.USER, user.getRole());
    }

    /**
     * This test checks if the user can be verified with the assigned id.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void verifyUser() {
        String wrongId = "100";
        userService.signUp(userBody);
        String userId = userRepository.findByEmail(userBody.getEmail()).getActivationId();
        assertDoesNotThrow(() -> userService.verifyUser(userId));
        assertThrows(NotFoundException.class, () -> userService.verifyUser(wrongId));
    }

    /**
     * Test if a user can sign in with the right and with the wrong credentials
     *
     * @throws Exception throws an exception when the login does not work with the right credentials
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void signIn() throws Exception {
        userService.signUp(userBody);
        User user = userRepository.findByEmail("hans.muster@mustermail.ch");
        user.setActivated(true);
        userRepository.save(user);
        SignInBody signInBody = new SignInBody();
        signInBody.setEmail("hans.muster@mustermail.ch");
        signInBody.setPassword("ZH_a?!WD32");
        var response = userService.signIn(signInBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());

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
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void passwordreset() {
        userService.signUp(userBody);
        User user = userRepository.findByEmail("hans.muster@mustermail.ch");
        user.setActivated(true);
        userRepository.save(user);
        String resetPasswordToken = userService.createResetPasswordToken(user);

        ResetPasswordBody resetPasswordBody = new ResetPasswordBody( "newPassword12!");
        assertThrows(Exception.class, () -> userService.resetPassword("badToken",resetPasswordBody));
        ResetPasswordBody resetPasswordBody1 = new ResetPasswordBody("badPassword");
        assertThrows(Exception.class, () -> userService.resetPassword(resetPasswordToken,resetPasswordBody1));
        ResetPasswordBody resetPasswordBody2 = new ResetPasswordBody("newPassword12!");
        assertDoesNotThrow(() -> userService.resetPassword(resetPasswordToken,resetPasswordBody2));

        String resetPasswordToken2 = userService.createResetPasswordToken(user);
        user.setTokenValidDate(new Date(System.currentTimeMillis() - 7200 * 1000));
        userRepository.save(user);
        assertThrows(Exception.class, () -> userService.resetPassword(resetPasswordToken2,resetPasswordBody2));
    }

    /**
     * This test, checks if a user can be found via it's id
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getUser() {
        assertThrows(NotFoundException.class, () -> userService.getUser(1));
        userService.signUp(userBody);
        assertNotNull(userService.getUser(1));
    }

    /**
     * This test checks if all user can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllUser() {
        userService.signUp(userBody);
        userBody.setEmail("test1@gmail.com");
        userService.signUp(userBody);
        userBody.setEmail("test2@gmail.com");
        userService.signUp(userBody);
        assertEquals(3,userRepository.count());
        userBody.setEmail("test3@gmail.com");
        userService.signUp(userBody);
        assertEquals(4,userRepository.count());
    }

    /**
     * This test checks if all user can be found that exist in the database who have not been soft deleted
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllVisibleUser() {
        userService.signUp(userBody);
        userBody.setEmail("test1@gmail.com");
        userService.signUp(userBody);
        userBody.setEmail("test2@gmail.com");
        userService.signUp(userBody);
        userBody.setEmail("test3@gmail.com");
        userService.signUp(userBody);
        assertEquals(4,userRepository.count());
        userService.deleteUser(4);
        assertEquals(3,userRepository.findByIsDeleted(false).size());
        userService.deleteUser(3);
        assertEquals(2,userRepository.findByIsDeleted(false).size());
    }


    /**
     * This test checks if the fields values of an existing user can be edited
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editUser() {
        User userAdmin = userService.signUp(userBody);
        userBody.setEmail("test1@gmail.com");
        User userNotAdmin = userService.signUp(userBody);

        assertEquals(UserRole.ADMIN,userAdmin.getRole());
        assertEquals(UserRole.USER,userNotAdmin.getRole());

        UserBody userBodyChanged = new UserBody();
        userBodyChanged.setFirstName("Maya");
        userBodyChanged.setLastName("Meier");
        userBodyChanged.setStreet("Inkastrasse");
        userBodyChanged.setPlace("Azteken");
        userBodyChanged.setZip("666");
        userBodyChanged.setPhoneNumber("077 777 66 77");
        userBodyChanged.setRole(UserRole.USER);
        userAdmin = userService.editUser(userAdmin.getId(), userBodyChanged);
        userBodyChanged.setRole(UserRole.ADMIN);
        userNotAdmin = userService.editUser(userNotAdmin.getId(), userBodyChanged);
        checkUserFields(userBodyChanged, userAdmin);
        checkUserFields(userBodyChanged, userNotAdmin);
    }

    /**
     * This method helps checking if the values are correct
     * @param userBody The user parameters
     * @param user the instance of a user
     */
    private void checkUserFields(UserBody userBody, User user) {
        assertEquals(userBody.getFirstName(), user.getFirstName());
        assertEquals(userBody.getLastName(), user.getLastName());
        assertEquals(userBody.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userBody.getPlace(), user.getPlace());
        assertEquals(userBody.getZip(), user.getZip());
        assertEquals(userBody.getStreet(), user.getStreet());
        if (user.getRole().equals(UserRole.ADMIN)) {
            assertEquals(userBody.getRole(), user.getRole());
        } else {
            assertNotEquals(userBody.getRole(), user.getRole());
        }
    }

    /**
     * This test, checks if an Admin can change the Userrole of others
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser() {
        User userAdmin = userService.signUp(userBody);
        assertTrue(userAdmin.getInitialAdmin());
        assertFalse(userAdmin.getIsDeleted());

        userBody.setEmail("test1@gmail.com");
        User secondUser = userService.signUp(userBody);
        assertFalse(secondUser.getInitialAdmin());
        assertFalse(secondUser.getIsDeleted());

        userAdmin = userService.deleteUser(1);
        assertFalse(userAdmin.getIsDeleted());
        secondUser = userService.deleteUser(2);
        assertTrue(secondUser.getIsDeleted());


    }


}
