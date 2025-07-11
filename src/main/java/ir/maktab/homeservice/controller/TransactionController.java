package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.TransactionResponse;
import ir.maktab.homeservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    //✅
    @GetMapping("/{walletId}")
    public ResponseEntity<List<TransactionResponse>> findAllForWalletId(
            @PathVariable Long walletId) {
        return ResponseEntity.ok(
               transactionService.findAllForSpecialist(walletId));
    }
}
