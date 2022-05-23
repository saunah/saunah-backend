package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingPrice;
import ch.saunah.saunahbackend.model.BookingSauna;
import ch.saunah.saunahbackend.model.User;

/**
 * This class tests the email method.
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Autowired
    @InjectMocks
    private MailService mailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
    }

    /**
     * Test that no exception is thrown if email address is in valid format.
     */
    @Test
    void testSendMailValidAddress() {
        Booking booking = new Booking();
        BookingPrice bookingPrice = new BookingPrice();
        BookingSauna bookingSauna = new BookingSauna();
        booking.setBookingPrice(bookingPrice);
        booking.setBookingSauna(bookingSauna);
        User user = new User();
        user.setEmail("hello@example.test");
        String verificationId = "1";
        String resetToken = "12345";
        assertDoesNotThrow(() -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        assertDoesNotThrow(() -> mailService.sendPasswordResetMail(user.getEmail(), resetToken));
        assertDoesNotThrow(() -> mailService.sendPasswordResetMail(user.getEmail(), resetToken));
        assertDoesNotThrow(() -> mailService.sendUserOpenedBookingMail(user.getEmail(), booking));
        assertDoesNotThrow(() -> mailService.sendUserEditedBookingMail(user.getEmail(), booking));
        assertDoesNotThrow(() -> mailService.sendUserApprovedBookingMail(user.getEmail(), booking));
        assertDoesNotThrow(() -> mailService.sendUserCanceledBookingMail(user.getEmail(), booking));
        assertDoesNotThrow(() -> mailService.sendAdminOpenedBookingMail(user, booking));
    }

}
