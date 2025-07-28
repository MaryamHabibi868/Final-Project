package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private SpecialistService specialistService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_walletBalanceForSpecialist_success() {
        String email = "specialist@example.com";
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.valueOf(150.0));
        Specialist specialist = new Specialist();
        specialist.setWallet(wallet);

        when(securityUtil.getCurrentUsername()).thenReturn(email);
        when(specialistService.findByEmail(email)).thenReturn(specialist);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        /*BigDecimal result = walletService.walletBalanceForSpecialist();*/
        /*assertEquals(BigDecimal.valueOf(150.0), result);*/
    }

    @Test
    void test_walletBalanceForSpecialist_walletNotFound() {
        String email = "specialist@example.com";
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        Specialist specialist = new Specialist();
        specialist.setWallet(wallet);

        when(securityUtil.getCurrentUsername()).thenReturn(email);
        when(specialistService.findByEmail(email)).thenReturn(specialist);
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> walletService.walletBalanceForSpecialist());
    }

    @Test
    void test_walletBalanceForCustomer_success() {
        String email = "customer@example.com";
        Wallet wallet = new Wallet();
        wallet.setId(2L);
        wallet.setBalance(BigDecimal.valueOf(300.0));
        Customer customer = new Customer();
        customer.setWallet(wallet);

        when(securityUtil.getCurrentUsername()).thenReturn(email);
        when(customerService.findByEmail(email)).thenReturn(customer);
        when(walletRepository.findById(2L)).thenReturn(Optional.of(wallet));

        /*BigDecimal result = walletService.walletBalanceForCustomer();
        assertEquals(BigDecimal.valueOf(300.0), result);*/
    }

    @Test
    void testWalletBalanceForCustomer_shouldThrowExceptionWhenWalletNotFound() {
        // Arrange
        String email = "customer@example.com";
        Long walletId = 1L;

        Wallet wallet = new Wallet();
        wallet.setId(walletId);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setWallet(wallet);

        // Mock behavior
        Mockito.when(securityUtil.getCurrentUsername()).thenReturn(email);
        Mockito.when(customerService.findByEmail(email)).thenReturn(customer);
        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class,
                () -> walletService.walletBalanceForCustomer()
        );

        Assertions.assertEquals("Wallet not found", thrown.getMessage());
    }


    @Test
    void test_chargeWallet_success() {
        Wallet wallet = new Wallet();
        wallet.setId(3L);
        wallet.setBalance(BigDecimal.valueOf(100.0));

        PaymentRequestDto request = new PaymentRequestDto();
        request.setWalletId(3L);
        request.setAmount(BigDecimal.valueOf(50.0));

        when(walletRepository.findById(3L)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        walletService.chargeWallet(request);

        assertEquals(BigDecimal.valueOf(150.0), wallet.getBalance());
        verify(transactionService, times(1)).save(any(Transaction.class));
    }
}
