package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.User;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.WalletResponse;
import ir.maktab.homeservice.dto.WalletSaveRequest;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.WalletMapper;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;

@Service
public class WalletServiceImpl
        extends BaseServiceImpl<Wallet, Long, WalletRepository>
        implements WalletService {

    private final WalletMapper walletMapper;

    public WalletServiceImpl(WalletRepository repository,
                             WalletMapper walletMapper) {
        super(repository);
        this.walletMapper = walletMapper;
    }

    //✅
    @Transactional
    @Override
    public WalletResponse createWallet(WalletSaveRequest request) {
        Wallet wallet = new Wallet();
        wallet.setBalance(request.getBalance());
        wallet.setUserInformation(User.builder().id(request.getUserId()).build());
        Wallet save = repository.save(wallet);
        return walletMapper.EntityMapToResponse(save);
    }

    //✅
    @Transactional
    @Override
    public BigDecimal walletBalance(Long walletId) {
        Wallet foundWallet = repository.findById(walletId).orElseThrow(
                () -> new NotFoundException("Wallet not found"));
        return foundWallet.getBalance();
    }
}
