package ir.maktab.homeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;


    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendVerificationEmail(String to, String token) {
        String subject = "Activate Your HomeService Account";
        String activationLink = "http://localhost:8080/api/v1/customers/verify?token=" + token;
        String content = "Hi,\n\nPlease click the following link to verify your email address:\n"
                + activationLink + "\n\nThis link is valid for 24 hours and can be used only once.";

        sendEmail(to, subject, content);
    }

    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
