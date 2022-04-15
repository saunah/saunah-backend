package ch.saunah.saunahbackend.jwt;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the JWT Token creation and their corresponding methods.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaunahBackendApplication.class)
public class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * This method checks if a token is generated correctly.
     */
    @Test
    void generateToken() {
        String userName = "username";
        String differentUserName = "differentUserName";
        User user = new User(userName, "password", new ArrayList<>());
        String jwtToken = jwtTokenUtil.generateToken(user);
        assertTrue(jwtTokenUtil.validateToken(jwtToken, user));
        user = new User(differentUserName, "password", new ArrayList<>());
        assertFalse(jwtTokenUtil.validateToken(jwtToken, user));
        assertFalse(jwtTokenUtil.getExpirationDateFromToken(jwtToken).before(new Date()));
        assertEquals(userName, jwtTokenUtil.getUsernameFromToken(jwtToken));
        user = new User(differentUserName, "password", new ArrayList<>());
        jwtToken = jwtTokenUtil.generateToken(user);
        assertNotEquals(userName, jwtTokenUtil.getUsernameFromToken(jwtToken));
    }
}
