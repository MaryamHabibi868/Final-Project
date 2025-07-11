package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.WalletResponse;
import ir.maktab.homeservice.dto.WalletSaveRequest;
import ir.maktab.homeservice.service.base.BaseService;


import java.math.BigDecimal;

public interface WalletService extends BaseService<Wallet, Long> {

    //✅
    WalletResponse createWallet(WalletSaveRequest request);

    //✅
    BigDecimal walletBalance(Long walletId);
}
