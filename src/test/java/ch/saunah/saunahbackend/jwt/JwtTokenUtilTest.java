package ch.saunah.saunahbackend.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.security.JwtTokenUtil;

/**
 * This class tests the JWT Token creation and their corresponding methods.
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class JwtTokenUtilTest {

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
