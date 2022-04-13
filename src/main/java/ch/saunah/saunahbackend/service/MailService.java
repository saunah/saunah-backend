package ch.saunah.saunahbackend.service;


import ch.saunah.saunahbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
     * @throws MessagingException if the message does not reach the target email
     */
    public void sendUserActivationMail(String email, int verificationId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(senderMail);
        helper.setTo(email);
        helper.setSubject("Singeup authentication");
        helper.setText("<p>Bitte klicken sie auf den Link, um ihren Account zu aktivieren: " +
            "<br><a href=\"" + frontendBaseUrl + "/signup/" + verificationId + "\">Hier drücken</a></p>", true);
        javaMailSender.send(mimeMessage);

    }
}
