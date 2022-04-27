package ch.saunah.saunahbackend.service;


import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    @Value("${saunah.email.from.name}")
    private String senderName;


    /**
     * This method sends a message authentication link to the email of the user.
     *
     * @param email The email of the user
     * @param verificationId The verification id of the user
     */
    public void sendUserActivationMail(String email, String verificationId) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(email);
            helper.setSubject("Signup authentication");
            helper.setText("<p>Bitte klicken sie auf den Link, um ihren Account zu aktivieren: " +
                "<br><a href=\"" + frontendBaseUrl + "/verify/" + verificationId + "\">Hier drücken</a></p>", true);
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException | MailException | UnsupportedEncodingException exception){
            System.err.printf(DEFAULT_MAIL_ERROR_MESSAGE, exception.getMessage());
        }
    }
    /**
     * This method sends a message authentication link to the email of the user.
     *
     * @param email The email of the user
     * @param userID The internal id of the user
     * @param resetPasswordToken This token will be used for the authentification for the reset
     */

    public void sendPasswordResetMail(String email, int userID , double resetPasswordToken) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(senderMail);
            helper.setTo(email);
            helper.setSubject("Password Reset SauNah");
            helper.setText("<p>Ihr Reset Token lautet: <h1>"+ resetPasswordToken +"</h1></p><p>Bitte klicken sie auf den Link, falls Sie Ihren Passwort vergessen haben : " +
                "<br><a href=\"" + frontendBaseUrl + "/resetPassword/" + userID + "\">Hier drücken</a></p>", true);
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException exception){
            System.err.printf("Error while sending the activation mail: %s\n", exception.getMessage());
        }
    }
}
