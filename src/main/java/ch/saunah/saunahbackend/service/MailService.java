package ch.saunah.saunahbackend.service;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ch.saunah.saunahbackend.exception.SaunahMailException;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.User;

/**
 * This provides methods to send emails.
 */
@Service
public class MailService {
    protected final Log logger = LogFactory.getLog(getClass());

    private static final String DEFAULT_MAIL_ERROR_MESSAGE = "Error while sending the activation mail: %s%n";
    private static final String VERIFY_URL_TEMPLATE = "%s/verify/%s";
    private static final String RESET_URL_TEMPLATE = "%s/reset-password/%s";
    private static final String BOOKING_URL_TEMPLATE = "%s/bookings/%s";
    private static final String VIEW_BOOKING_TEXT_TEMPLATE = "<p>Hier können Sie Ihre Buchung einsehen: <a href=\"%s\">%s</a></p>";

    @Value("${saunah.frontend.baseurl}")
    private String frontendBaseUrl;

    @Value("${saunah.email.from.email}")
    private String senderEmail;

    @Value("${saunah.email.from.reply-to}")
    private String replyToEmail;

    @Value("${saunah.email.from.name}")
    private String senderName;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * This method sends a message authentication link to the email of the user.
     *
     * @param email          The email of the user
     * @param verificationId The verification id of the user
     * @throws SaunahMailException is thrown when the mail was not sent.
     */
    public void sendUserActivationMail(String email, String verificationId) {
        String subject = "Aktivieren Sie Ihren Account";
        String verificationUrl = String.format(VERIFY_URL_TEMPLATE, frontendBaseUrl, verificationId);
        String message = String.format(
            "<p>Sie haben sich für einen neuen Account auf %s angemeldet</p>" +
                "<p>Bitte klicken Sie auf den Link, um Ihren Account zu aktivieren: <a href=\"%s\">%s</a></p>",
            frontendBaseUrl,
            verificationUrl,
            verificationUrl
        );

        sendMail(email, subject, message);
    }

    /**
     * This method sends a message authentication link to the email of the user.
     *
     * @param email              The email of the user
     * @param resetPasswordToken This token will be used for the authentification for the reset
     * @throws SaunahMailException is thrown when the mail was not sent.
     */
    public void sendPasswordResetMail(String email, String resetPasswordToken) {
        String subject = "Setzen Sie Ihr Passwort zurück";
        String resetPasswordUrl = String.format(RESET_URL_TEMPLATE, frontendBaseUrl, resetPasswordToken);
        String message = String.format(
            "<p>Jemand hat das Zurücksetzen Ihres Passwortes auf %s angefordert.</p>" +
                "<p>Falls das Sie waren, klicken Sie bitte innerhalb von einer Stunde auf den folgenden Link, " +
                "um ein neues Passwort zu setzen: <a href=\"%s\">%s</a></p>",
            frontendBaseUrl,
            resetPasswordUrl,
            resetPasswordUrl
        );

        sendMail(email, subject, message);
    }

    /**
     * This method sends a mail which informs the user that his booking has been opened.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserOpenedBookingMail(String email, Booking booking) {
        String subject = "Ihre Buchungsanfrage wurde versendet";
        String bookingUrl = String.format(BOOKING_URL_TEMPLATE, frontendBaseUrl, booking.getId());
        String message = String.format(
            "<p>Ihre Buchung wurde erfolgreich eröffnet.</p>" +
                VIEW_BOOKING_TEXT_TEMPLATE,
            bookingUrl,
            bookingUrl
        );

        sendMail(email, subject, message);
    }

    /**
     * This method sends a mail which informs the user that his booking has been approved.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserApprovedBookingMail(String email, Booking booking) {
        String subject = "Buchung bestätigt";
        String bookingUrl = String.format(BOOKING_URL_TEMPLATE, frontendBaseUrl, booking.getId());
        String message = String.format(
            "<p>Ihre Buchung wurde bestätigt.</p>" +
                VIEW_BOOKING_TEXT_TEMPLATE,
            bookingUrl,
            bookingUrl
        );

        sendMail(email, subject, message);
    }

    /**
     * This method sends a mail which informs the user that his booking has been canceled.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserCanceledBookingMail(String email, Booking booking) {
        String subject = "Buchung storniert";
        String bookingUrl = String.format(BOOKING_URL_TEMPLATE, frontendBaseUrl, booking.getId());
        String message = String.format(
            "<p>Ihre Buchung wurde storniert.</p>" +
                VIEW_BOOKING_TEXT_TEMPLATE,
            bookingUrl,
            bookingUrl
        );

        sendMail(email, subject, message);
    }

    /**
     * This method sends a mail which informs the user that his booking has been edited.
     *
     * @param email   The email of the user
     * @param booking The Booking info
     */
    public void sendUserEditedBookingMail(String email, Booking booking) {
        String subject = "Buchung bearbeitet";
        String bookingUrl = String.format(BOOKING_URL_TEMPLATE, frontendBaseUrl, booking.getId());
        String message = String.format(
            "<p>Ihre Buchung wurde bearbeitet.</p>" +
                VIEW_BOOKING_TEXT_TEMPLATE,
            bookingUrl,
            bookingUrl
        );

        sendMail(email, subject, message);
    }

    /**
     * This method sends a mail to all admins that a new booking has been created.
     *
     * @param booking The Booking info
     */
    public void sendAdminOpenedBookingMail(User admin, Booking booking) {
        String subject = "Neue Buchung";
        String bookingUrl = String.format(BOOKING_URL_TEMPLATE, frontendBaseUrl, booking.getId());
        String message = String.format(
            "<p>Eine neue Buchung wurde eröffnet.</p>" +
                "<p>Hier kann die Buchung eingesehen werden: <a href=\"%s\">%s</a></p>",
            bookingUrl,
            bookingUrl
        );

        sendMail(admin.getEmail(), subject, message);
    }

    /**
     * Send email from default sender address.
     *
     * @param receiverEmail The receiver of the message.
     * @param subject       The subject of the message.
     * @param message       The message, HTML allowed.
     */
    private void sendMail(String receiverEmail, String subject, String message) {
        Thread newThread = new Thread(() -> {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

                helper.setFrom(new InternetAddress(senderEmail, senderName));
                helper.setReplyTo(getReplyToEmail());
                helper.setTo(receiverEmail);
                helper.setSubject(subject);
                helper.setText(message, true);

                javaMailSender.send(mimeMessage);
            } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
                logger.error(String.format(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage()));
            }
        });
        newThread.start();
    }

    /**
     * Get the default reply to email. In case of the replyTo value being
     * empty, the sender email will be returned.
     *
     * @return the default reply to email.
     */
    private String getReplyToEmail() {
        if (!replyToEmail.isBlank()) {
            return replyToEmail;
        }
        return senderEmail;
    }
}
