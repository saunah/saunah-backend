package ch.saunah.saunahbackend.service;


import java.io.UnsupportedEncodingException;

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

import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.User;

/**
 * This class contains the mail service.
 */
@Service
public class MailService {
    protected final Log logger = LogFactory.getLog(getClass());

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
     * @param resetPasswordToken This token will be used for the authentification for the reset
     */
    public void sendPasswordResetMail(String email, String resetPasswordToken) {
        try {
            String resetPasswordSubject = "Setzen Sie Ihr Passwort zurück";
            String resetPasswordUrl = String.format("%s/reset-password/%s", frontendBaseUrl, resetPasswordToken);
            String resetPasswordMessage = String.format(
                "<p>Jemand hat das Zurücksetzen Ihres Passwortes auf %s angefordert.</p>" +
                "<p>Falls das Sie waren, klicken Sie bitte innerhalb von einer Stunde auf den folgenden Link, " +
                "um ein neues Passwort zu setzen: <a href=\"%s\">%s</a></p>",
                frontendBaseUrl,
                resetPasswordUrl,
                resetPasswordUrl
            );

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setReplyTo(new InternetAddress(replyToEmail, senderName));
            helper.setTo(email);
            helper.setSubject(resetPasswordSubject);
            helper.setText(resetPasswordMessage, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
            logger.error(String.format(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage()));
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
            helper.setText(mailText +
                "<br><a href=\"" + frontendBaseUrl + "/bookings/" + booking.getId() + "/" + "\">zur Buchung</a></p>", true);
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
