package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingPrice;
import ch.saunah.saunahbackend.model.BookingSauna;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.model.User;

import java.util.List;

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
        User user = new User();
        user.setEmail("test@mail.ch");
        String verificationId = "1";
        int userID= 1111;
        int resetToken = 12345;
        assertDoesNotThrow(() -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        assertDoesNotThrow(() ->mailService.sendPasswordResetMail(user.getEmail(), userID ,resetToken));
        assertDoesNotThrow(() ->mailService.sendPasswordResetMail(user.getEmail(), userID ,resetToken));
        assertDoesNotThrow(() -> mailService.sendUserOpenedBookingMail(user.getEmail(), booking, bookingPrice, bookingSauna));
        assertDoesNotThrow(() -> mailService.sendUserApprovedBookingMail(user.getEmail(), booking, bookingPrice, bookingSauna));
        assertDoesNotThrow(() -> mailService.sendUserCanceledBookingMail(user.getEmail(), booking, bookingPrice, bookingSauna));
        assertDoesNotThrow(() -> mailService.sendAdminOpenedBookingMail(List.of(user), booking));
        user.setEmail("bad email");
        assertDoesNotThrow(() -> mailService.sendUserActivationMail(user.getEmail(), verificationId));
        assertDoesNotThrow(() ->mailService.sendPasswordResetMail(user.getEmail(), userID ,resetToken));
        assertDoesNotThrow(() -> mailService.sendUserOpenedBookingMail(user.getEmail(), booking, bookingPrice, bookingSauna));
        assertDoesNotThrow(() -> mailService.sendUserApprovedBookingMail(user.getEmail(), booking, bookingPrice, bookingSauna));
        assertDoesNotThrow(() -> mailService.sendUserCanceledBookingMail(user.getEmail(), booking, bookingPrice, bookingSauna));
        assertDoesNotThrow(() -> mailService.sendAdminOpenedBookingMail(List.of(user), booking));

    }
}
