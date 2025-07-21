package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
public class WalletServiceImpl
        extends BaseServiceImpl<Wallet, Long, WalletRepository>
        implements WalletService {


    private final CustomerService customerService;
    private final TransactionService transactionService;


    public WalletServiceImpl(WalletRepository repository,
                             CustomerService customerService,
                             TransactionService transactionService) {
        super(repository);
        this.customerService = customerService;
        this.transactionService = transactionService;
    }


    @Transactional
    @Override
    public BigDecimal walletBalance(Long walletId) {
        Wallet foundWallet = repository.findById(walletId).orElseThrow(
                () -> new NotFoundException("Wallet not found"));
        return foundWallet.getBalance();
    }

    @Override
    @Transactional
    public void chargeWallet(Long customerId, BigDecimal amount) {
        Customer customer = customerService.findById(customerId);
        Wallet wallet = customer.getWallet();
        wallet.setBalance(wallet.getBalance().add(amount));
        save(wallet);


        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(ZonedDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setWallet(wallet);
        transactionService.save(transaction);
    }
}
