package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.service.base.BaseService;
import java.math.BigDecimal;

public interface WalletService extends BaseService<Wallet, Long> {

    BigDecimal walletBalanceForSpecialist(/*Long walletId*/);

    void chargeWallet(PaymentRequestDto request);

    BigDecimal walletBalanceForCustomer();
}
