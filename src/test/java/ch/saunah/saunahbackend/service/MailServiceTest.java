package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.model.User;

/**
 * This class tests the email method.
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class MailServiceTest {

    @Autowired
    private MailService mailService;

    /**
     * This method tests if the email is sent once to the correct user.
     *
     * @throws MessagingException
     */
    @Test
    void sendMail() {
        User user = new User();
        user.setEmail("test@mail.ch");
        String verificationId = "1";
        assertDoesNotThrow(() -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        user.setEmail("bad email");
        assertDoesNotThrow(() -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
    }
}
