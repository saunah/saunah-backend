package ch.saunah.saunahbackend.service;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * This class contains the mail service.
 */
@Service
public class MailService {

    private static final String DEFAULT_MAIL_ERROR_MESSAGE = "Error while sending the activation mail: %s%n";

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${saunah.frontend.baseurl}")
    private String frontendBaseUrl;

    @Value("${saunah.email.from.email}")
    private String senderEmail;

    @Value("${saunah.email.from.reply-to}")
    private String replyToEmail;

    @Value("${saunah.email.from.name}")
    private String senderName;


    /**
     * This method sends a message authentication link to the email of the user.
     *
     * @param email          The email of the user
     * @param verificationId The verification id of the user
     */
    public void sendUserActivationMail(String email, String verificationId) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setReplyTo(new InternetAddress(getReplyToEmail(), senderName));
            helper.setTo(email);
            helper.setSubject("Signup authentication SauNah");
            helper.setText("<p>Bitte klicken sie auf den Link, um ihren Account zu aktivieren: " +
                "<br><a href=\"" + frontendBaseUrl + "/verify/" + verificationId + "\">Hier klicken</a></p>", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
            System.err.printf(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage());
        }
    }

    /**
     * This method sends a message authentication link to the email of the user.
     *
     * @param email              The email of the user
     * @param userID             The internal id of the user
     * @param resetPasswordToken This token will be used for the authentification for the reset
     */
    public void sendPasswordResetMail(String email, int userID, int resetPasswordToken) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setReplyTo(new InternetAddress(replyToEmail, senderName));
            helper.setTo(email);
            helper.setSubject("Password Reset SauNah");
            helper.setText("<p>Ihr Reset Token lautet: <h1>" + resetPasswordToken + "</h1></p><p>Bitte klicken sie auf den Link, falls Sie Ihren Passwort vergessen haben : " +
                "<br><a href=\"" + frontendBaseUrl + "/resetPassword/" + userID + "\">Hier klicken</a></p>", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
            System.err.printf(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage());
        }
    }

    /**
     * This method sends a mail which informs the user that his booking has been opened.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserOpenedBookingMail(String email, Booking booking) {
        String mailText = "<p>Ihre Buchung wurde erfolgreich eröffnet. Hier sehen sie ihre neue Buchung:</p>";
        messageMailSetup(email, mailText, booking);
    }

    /**
     * This method sends a mail which informs the user that his booking has been approved.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserApprovedBookingMail(String email, Booking booking) {
        String mailText = "<p>Ihre Buchung wurde genehmigt. Hier sehen sie die Buchung:</p>";
        messageMailSetup(email, mailText, booking);
    }

    /**
     * This method sends a mail which informs the user that his booking has been canceled.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserCanceledBookingMail(String email, Booking booking) {
        String mailText = "<p>Ihr Buchung wurde storniert. Hier sehen sie die Buchung:</p>";
        messageMailSetup(email, mailText, booking);
    }

    /**
     * This method sends a mail which informs the user that his booking has been edited.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserEditedBookingMail(String email, Booking booking) {
        String mailText = "<p>Ihr Buchung wurde verändert. Hier sehen sie die Buchung:</p>";
        messageMailSetup(email, mailText, booking);
    }

    private void messageMailSetup(String email, String mailText, Booking booking) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(email);
            helper.setSubject("Booking Info SauNah");
            helper.setText(mailText + generateBookingInfoMessage(booking), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
            System.err.printf(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage());
        }
    }

    /**
     * This method sends a mail to all admins that a new booking has been created.
     *
     * @param booking The Booking info
     */
    public void sendAdminOpenedBookingMail(User admin, Booking booking) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(admin.getEmail());
            helper.setSubject("Booking Info SauNah");
            helper.setText("<p>Eine neue Buchung wurde eröffnet. Hier sehen sie die Buchung:<h1>" +
                "<br><a href=\"" + frontendBaseUrl + "/bookings/" + booking.getId() + "/" + "\">zur Buchung</a></p>", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
            System.err.printf(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage());
        }
    }

    private String generateBookingInfoMessage(Booking booking) {
        return
            "<p>Buchungsnummer: " + booking.getId() + "</p>" +
                "<p>UserId: " + booking.getUserId() + "</p>" +
                "<p>Erstellungsdatum der Buchung:" + booking.getCreation() + "</p>" +
                "<p>Buchung Start Datum: " + booking.getStartBookingDate() + "</p>" +
                "<p>Buchung End Datum: " + booking.getEndBookingDate() + "</p>" +
                "<p>SaunaId: " + booking.getBookingSauna().getSaunaId() + "</p>" +
                "<p>Sauna: " + booking.getBookingSauna().getSaunaName() + "</p>" +
                "<p>Sauna Typ: " + booking.getBookingSauna().getSaunaType() + "</p>" +
                "<p>Sauna Ort: " + booking.getBookingSauna().getSaunaLocation() + "</p>" +
                "<p>Sauna Strasse: " + booking.getBookingSauna().getSaunaStreet() + "</p>" +
                "<p>Sauna PLZ: " + booking.getBookingSauna().getSaunaZip() + "</p>" +
                "<p>Sauna Beschreibung: " + booking.getBookingSauna().getSaunaDescription() + "</p>" +
                "<p>Sauna maximale Temperatur: " + booking.getBookingSauna().getSaunaMaxTemp() + "</p>" +
                "<p>Sauna Personen Kapazität: " + booking.getBookingSauna().getSaunaNumberOfPeople() + "</p>" +
                "<p>Ihr angegebener Ort: " + booking.getLocation() + "</p>" +
                "<p>Transport Service Distanz in km: " + booking.getTransportServiceDistance() + "</p>" +
                "<p>Transport Service Preis: " + booking.getBookingPrice().getTransportServicePrice() + "</p>" +
                "<p>Wasch Service Preis: " + booking.getBookingPrice().getWashServicePrice() + "</p>" +
                "<p>Imp Anzahl: " + booking.getSaunahImpAmount() + "</p>" +
                "<p>Imp Preis: " + booking.getBookingPrice().getSaunahImpPrice() + "</p>" +
                "<p>Deposit Preis: " + booking.getBookingPrice().getDepositPrice() + "</p>" +
                "<p>Handtuch Anzahl: " + booking.getHandTowelAmount() + "</p>" +
                "<p>Handtuch Preis: " + booking.getBookingPrice().getHandTowelPrice() + "</p>" +
                "<p>Brennholz Anzahl: " + booking.getWoodAmount() + "</p>" +
                "<p>Brennholz Preis: " + booking.getBookingPrice().getWoodPrice() + "</p>" +
                "<p>Sauna Preis: " + booking.getBookingSauna().getSaunaPrice() + "</p>" +
                "<p>Totalpreis: " + booking.getEndPrice() + "</p>";

    /**
     * Get the default reply to email. In case of the replyTo value being
     * empty, the sender email will be returned.
     * @return the default reply to email.
     */
    private String getReplyToEmail() {
        if (!replyToEmail.isBlank()) {
            return replyToEmail;
        }
        return senderEmail;
    }
}
