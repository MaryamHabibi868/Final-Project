package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class WalletServiceImpl
        extends BaseServiceImpl<Wallet, Long, WalletRepository>
        implements WalletService {

    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
    }


    @Transactional
    @Override
    public BigDecimal walletBalance(Long walletId) {
        Wallet foundWallet = repository.findById(walletId).orElseThrow(
                () -> new NotFoundException("Wallet not found"));
        return foundWallet.getBalance();
    }
}
