package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.security.JwtTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    UserService userService;

    SignUpBody signUpBody = null;

    User user = null;



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

        user = new User();
        user.setActivated(false);
        user.setEmail("hans.muster@mustermail.ch");
        user.setFirstName("Hans");
        user.setLastName("Muster");
        user.setPlace("Winterthur");
        user.setPhoneNumber("0123");
        user.setStreet("Teststrasse 123");
        user.setPasswordHash("testhash");
        user.setRole(UserRole.USER);
        userService = new UserService(authenticationManager,jwtTokenUtil,userRepository,userDetailsService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void signUpThorwCheck(){
        when(userRepository.findByEmail("hans.muster@mustermail.ch")).thenReturn(user);
        Exception exception = assertThrows(Exception.class, () -> {
            userService.signUp(signUpBody);
        });
        String expectedMessage = "Email already taken";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void signUpThorwCheckEmail(){
        signUpBody.setEmail("bademail");
        when(userRepository.findByEmail("bademail")).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> {
            userService.signUp(signUpBody);
        });
        String expectedMessage = "The email is not valid";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void signUp() throws Exception {
        when(userRepository.findByEmail("hans.muster@mustermail.ch")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);
        user.setPasswordHash("$2a$10$eV0ERtS4/4kLNKV.16TvSuCxe7eWg49JLvuKAmzfqsCAUWRIW/NYe");
        userService.signUp(signUpBody);
        verify(userRepository, times(1)).save(any());

    }

    @Test
    void verifyUser() {
        when(userRepository.findById(123)).thenReturn(Optional.of(user));
        when(userRepository.findById(1234)).thenReturn(Optional.empty());
        boolean returnValueFound = userService.verifyUser(123);
        boolean returnValueNull = userService.verifyUser(1234);

        assertTrue(returnValueFound);
        assertFalse(returnValueNull);


    }

    @Test
    void signIn() {
    }
}
