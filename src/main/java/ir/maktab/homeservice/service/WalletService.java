package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.dto.WalletResponse;
import ir.maktab.homeservice.service.base.BaseService;

public interface WalletService extends BaseService<Wallet, Long> {

    WalletResponse walletBalanceForSpecialist();

    void chargeWallet(PaymentRequestDto request);

    WalletResponse walletBalanceForCustomer();
}
