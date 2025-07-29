package ir.maktab.homeservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private EmailService emailService;
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() throws Exception {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);

        Field fromEmailField = EmailService.class
                .getDeclaredField("fromEmail");
        fromEmailField.setAccessible(true);
        fromEmailField.set(emailService, "test@example.com");
    }

    @Test
    void testSendVerificationEmail() {
        String to = "user@example.com";
        String token = "abc123";

        emailService.sendVerificationEmail(to, token);

        ArgumentCaptor<SimpleMailMessage> captor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals("test@example.com", sentMessage.getFrom());
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals("Activate Your HomeService Account",
                sentMessage.getSubject());
        assertEquals(true, sentMessage.getText()
                .contains("http://localhost:8080/api/v1/customers/verify?token=abc123"));
    }
}
