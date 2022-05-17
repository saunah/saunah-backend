package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.mail.MessagingException;

import ch.saunah.saunahbackend.exception.SaunahMailException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.model.User;
import org.springframework.mail.MailException;

/**
 * This class tests the email method.
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class MailServiceTest {

    @Autowired
    private MailService mailService;

    /**
     * This method tests if the email is sent once to the correct user.
     */
    @Test
    void sendMail() {
        User user = new User();
        user.setEmail("test@mail.ch");
        String verificationId = "1";
        int userID= 1111;
        int resetToken = 12345;
        assertThrows(SaunahMailException.class, () -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        assertThrows(SaunahMailException.class, () ->mailService.sendPasswordResetMail(user.getEmail(), userID ,resetToken));
        user.setEmail("bad email");
        assertThrows(SaunahMailException.class, () -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
    }
}
