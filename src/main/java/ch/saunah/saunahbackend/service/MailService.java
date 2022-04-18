package ch.saunah.saunahbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * This class contains the mail service.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${saunah.frontend.baseurl}")
    private String frontendBaseUrl;
    @Value("${spring.mail.username}")
    private String senderMail;


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
            helper.setFrom(senderMail);
            helper.setTo(email);
            helper.setSubject("Signup authentication");
            helper.setText("<p>Bitte klicken sie auf den Link, um ihren Account zu aktivieren: " +
                "<br><a href=\"" + frontendBaseUrl + "/verify/" + verificationId + "\">Hier drücken</a></p>", true);
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException exception){
            System.err.printf("Error while sending the activation mail: %s\n", exception.getMessage());
        }
        catch (MailException exception){
            System.err.printf("Error while sending the activation mail: %s\n", exception.getMessage());
        }
    }
}
