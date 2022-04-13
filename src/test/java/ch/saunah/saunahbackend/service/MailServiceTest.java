package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

/**
 * This class tests the email method.
 */
@SpringBootTest
class MailServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    MailService mailService;

    User user = null;


    @BeforeEach
    void setUp() {
        mailService = new MailService(javaMailSender);
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
    }

    /**
     * This method tests if the email is sent once to the correct user.
     *
     * @throws MessagingException
     */
    @Test
    void sendMail() throws MessagingException {
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        mailService.sendMail(user);
        verify(javaMailSender, times(1)).send((MimeMessage) any());
    }
}
