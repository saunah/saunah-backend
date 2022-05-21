package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.exception.SaunahMailException;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingPrice;
import ch.saunah.saunahbackend.model.BookingSauna;
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
     */
    @Test
    void sendMail() {
        Booking booking = new Booking();
        BookingPrice bookingPrice = new BookingPrice();
        BookingSauna bookingSauna = new BookingSauna();
        booking.setBookingPrice(bookingPrice);
        booking.setBookingSauna(bookingSauna);
        User user = new User();
        user.setEmail("not-registered@example.test");
        String verificationId = "1";
        String resetToken = "12345";
        assertThrows(SaunahMailException.class, () -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        assertThrows(SaunahMailException.class, () -> mailService.sendPasswordResetMail(user.getEmail(), resetToken));
        assertThrows(SaunahMailException.class, () -> mailService.sendPasswordResetMail(user.getEmail(), resetToken));
        assertThrows(SaunahMailException.class, () -> mailService.sendUserOpenedBookingMail(user.getEmail(), booking));
        assertThrows(SaunahMailException.class, () -> mailService.sendUserApprovedBookingMail(user.getEmail(), booking));
        assertThrows(SaunahMailException.class, () -> mailService.sendUserCanceledBookingMail(user.getEmail(), booking));
        assertThrows(SaunahMailException.class, () -> mailService.sendAdminOpenedBookingMail(user, booking));
        user.setEmail("bad email");
        assertThrows(SaunahMailException.class, () -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        assertThrows(SaunahMailException.class, () -> mailService.sendPasswordResetMail(user.getEmail(), resetToken));
        assertThrows(SaunahMailException.class, () -> mailService.sendUserOpenedBookingMail(user.getEmail(), booking));
        assertThrows(SaunahMailException.class, () -> mailService.sendUserApprovedBookingMail(user.getEmail(), booking));
        assertThrows(SaunahMailException.class, () -> mailService.sendUserCanceledBookingMail(user.getEmail(), booking));
        assertThrows(SaunahMailException.class, () -> mailService.sendAdminOpenedBookingMail(user, booking));
    }
}
