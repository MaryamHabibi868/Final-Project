package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.CaptchaDto;
import ir.maktab.homeservice.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;


    @CrossOrigin("http://localhost:63342")
    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> newCaptcha() {
        return ResponseEntity.ok(captchaService.generate());
    }
}
