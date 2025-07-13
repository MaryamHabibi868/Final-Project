package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.WalletResponse;
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


    //✅
    @GetMapping("/{walletId}")
    public ResponseEntity<BigDecimal> walletBalance(
            @PathVariable Long walletId) {
        return ResponseEntity.ok(
                walletService.walletBalance(walletId));
    }
}
