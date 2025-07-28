package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.service.CaptchaService;
import ir.maktab.homeservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/wallets")
public class WalletController {

    private final WalletService walletService;
    private final CaptchaService captchaService;


    //✅
    @PreAuthorize("hasAnyAuthority('ROLE_SPECIALIST')")
    @GetMapping("/get-balance-for-specialist")
    public ResponseEntity<BigDecimal> walletBalanceForSpecialist(
           /* @PathVariable Long walletId*/
    ) {
        return ResponseEntity.ok(
                walletService.walletBalanceForSpecialist(/*walletId*/));
    }


    //✅
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER')")
    @GetMapping("/get-balance-for-customer")
    public ResponseEntity<BigDecimal> walletBalanceForCustomer(
            /* @PathVariable Long walletId*/
    ) {
        return ResponseEntity.ok(
                walletService.walletBalanceForCustomer(/*walletId*/));
    }



    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
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
