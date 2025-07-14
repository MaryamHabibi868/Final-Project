package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.service.CaptchaService;
import ir.maktab.homeservice.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final OfferService   offerService;
    private final CaptchaService captchaService;

    /**
     * POST /api/payment
     * دریافت اطلاعات پرداخت از صفحهٔ فرانت و انجام عملیات.
     */
    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestBody @Valid PaymentRequestDto dto) {

        /* 1) اعتبارسنجی کپچا */
        if (!captchaService.verify(dto.getCaptchaToken(), dto.getCaptchaAnswer())) {
            return ResponseEntity.badRequest().body("کپچا نادرست است.");
        }

        /* 2) (اختیاری) اعتبارسنجیِ ظاهری کارت */
        if (dto.getCardNumber() == null || dto.getCardNumber().length() != 16) {
            return ResponseEntity.badRequest().body("شماره کارت نامعتبر است.");
        }

        /* 3) انجام پرداخت */
        try {
            offerService.paySpecialist(dto.getOfferId());
            return ResponseEntity.ok("پرداخت با موفقیت انجام شد.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("خطا: " + ex.getMessage());
        }
    }
}
