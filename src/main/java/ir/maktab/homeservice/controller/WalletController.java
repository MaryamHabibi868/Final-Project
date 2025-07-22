package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.service.CaptchaService;
import ir.maktab.homeservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/wallets")
public class WalletController {

    private final WalletService walletService;
    private final CaptchaService captchaService;


    @GetMapping("/{walletId}")
    public ResponseEntity<BigDecimal> walletBalance(
            @PathVariable Long walletId) {
        return ResponseEntity.ok(
                walletService.walletBalance(walletId));
    }



    @CrossOrigin("http://localhost:63342")
    @PostMapping("/charge-wallet")
    public ResponseEntity<String> chargeWallet(
            @RequestBody @Valid PaymentRequestDto dto) {

        if (!captchaService.verify(dto.getCaptchaToken(), dto.getCaptchaAnswer())) {
            return ResponseEntity.badRequest().body("Wrong captcha");
        }

        if (dto.getCardNumber() == null || dto.getCardNumber().length() != 16) {
            return ResponseEntity.badRequest().body("Invalid card number");
        }

        try {
            walletService.chargeWallet(dto);
            return ResponseEntity.ok("Wallet charged");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }
}
