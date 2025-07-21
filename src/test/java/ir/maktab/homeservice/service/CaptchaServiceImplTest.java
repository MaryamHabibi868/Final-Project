package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.CaptchaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaptchaServiceImplTest {

    private CaptchaServiceImpl captchaService;

    @BeforeEach
    void setUp() {
        captchaService = new CaptchaServiceImpl();
    }

    @Test
    void generate_ShouldReturnCaptchaDtoWithTextAndImage() {
        CaptchaDto dto = captchaService.generate();

        assertNotNull(dto);
        assertNotNull(dto.getText());
        assertFalse(dto.getText().isEmpty());
        assertNotNull(dto.getImageBase64());
        assertFalse(dto.getImageBase64().isEmpty());
    }

    @Test
    void verify_ShouldReturnTrue_WhenCorrectAnswerGiven() {
        CaptchaDto dto = captchaService.generate();

        boolean result = captchaService.verify(dto.getText(), dto.getText());

        assertTrue(result);
    }

    @Test
    void verify_ShouldReturnFalse_WhenWrongAnswerGiven() {
        CaptchaDto dto = captchaService.generate();

        boolean result = captchaService.verify(dto.getText(), "wrongAnswer");

        assertFalse(result);
    }

    @Test
    void verify_ShouldReturnFalse_WhenTokenIsNull() {
        boolean result = captchaService.verify(null, "anything");

        assertFalse(result);
    }

    @Test
    void verify_ShouldReturnFalse_WhenUserAnswerIsNull() {
        CaptchaDto dto = captchaService.generate();

        boolean result = captchaService.verify(dto.getText(), null);

        assertFalse(result);
    }

    @Test
    void verify_ShouldRemoveCaptchaAfterSuccessfulVerification() {
        CaptchaDto dto = captchaService.generate();

        boolean firstTry = captchaService.verify(dto.getText(), dto.getText());
        boolean secondTry = captchaService.verify(dto.getText(), dto.getText());

        assertTrue(firstTry);
        assertFalse(secondTry);
    }
}

