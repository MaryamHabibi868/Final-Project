package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.dto.WalletResponse;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.WalletMapper;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

@Service
public class WalletServiceImpl
        extends BaseServiceImpl<Wallet, Long, WalletRepository>
        implements WalletService {

    private final TransactionService transactionService;
    private final SecurityUtil securityUtil;
    private final SpecialistService specialistService;
    private final CustomerService customerService;
    private final WalletMapper walletMapper;


    public WalletServiceImpl(WalletRepository repository,
                             TransactionService transactionService,
                             SecurityUtil securityUtil,
                             SpecialistService specialistService,
                             CustomerService customerService,
                             WalletMapper walletMapper) {
        super(repository);
        this.transactionService = transactionService;
        this.securityUtil = securityUtil;
        this.specialistService = specialistService;
        this.customerService = customerService;
        this.walletMapper = walletMapper;
    }


    @Transactional
    @Override
    public WalletResponse walletBalanceForSpecialist() {

        String email = securityUtil.getCurrentUsername();
        Specialist foundSpecialist = specialistService.findByEmail(email);
        Long walletId = foundSpecialist.getWallet().getId();

        Wallet foundWallet = repository.findById(walletId).orElseThrow(
                () -> new NotFoundException("Wallet not found"));
        return walletMapper.entityMapToWalletResponse(foundWallet);
    }

    @Override
    @Transactional
    public void chargeWallet(PaymentRequestDto request) {
        Wallet wallet = findById(request.getWalletId());
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        save(wallet);


        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDate(ZonedDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setWallet(wallet);
        transactionService.save(transaction);
    }

    @Override
    public WalletResponse walletBalanceForCustomer() {
        String email = securityUtil.getCurrentUsername();
        Customer foundCustomer = customerService.findByEmail(email);
        Long walletId = foundCustomer.getWallet().getId();

        Wallet foundWallet = repository.findById(walletId).orElseThrow(
                () -> new NotFoundException("Wallet not found"));
        return walletMapper.entityMapToWalletResponse(foundWallet);
    }
}
