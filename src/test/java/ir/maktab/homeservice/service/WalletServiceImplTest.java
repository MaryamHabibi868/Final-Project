package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void walletBalance_walletExists_returnsBalance() {
        Long walletId = 1L;
        BigDecimal balance = BigDecimal.valueOf(1000);

        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setBalance(balance);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        BigDecimal result = walletService.walletBalance(walletId);

        assertEquals(balance, result);
        verify(walletRepository).findById(walletId);
    }

    @Test
    void walletBalance_walletNotFound_throwsNotFoundException() {
        Long walletId = 2L;

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            walletService.walletBalance(walletId);
        });

        assertEquals("Wallet not found", exception.getMessage());
        verify(walletRepository).findById(walletId);
    }

    @Test
    void chargeWallet_shouldIncreaseBalanceAndSaveTransaction() {
        Long customerId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(200));

        Customer customer = new Customer();
        customer.setWallet(wallet);

        when(customerService.findById(customerId)).thenReturn(customer);
        when(walletRepository.save(any(Wallet.class)))
                .thenAnswer(i -> i.getArgument(0));
        when(transactionService.save(any(Transaction.class))).thenReturn(null);

        walletService.chargeWallet(customerId, amount);

        assertEquals(new BigDecimal("300"), wallet.getBalance());

        verify(walletRepository, times(1)).save(wallet);
        verify(transactionService, times(1))
                .save(any(Transaction.class));
    }
}
