package ch.saunah.saunahbackend.service;


import ch.saunah.saunahbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${saunah.frontend.baseurl}")
    private String frontendBaseUrl;

    public void sendMail(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("example@example.com"); //TODO: anpassen auf richtige Email
        helper.setTo(user.getEmail());
        helper.setSubject("Singeup authentication");
        helper.setText("<p>Bitte klicken sie auf den Link, um ihren Account zu aktivieren: " +
            "<br><a href=\"" + frontendBaseUrl + "/signup/" + user.getId() + "\">Hier drücken</a></p>", true);
        javaMailSender.send(mimeMessage);

    }
}