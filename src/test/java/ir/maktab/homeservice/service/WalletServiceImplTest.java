package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.domains.enumClasses.TransactionType;
import ir.maktab.homeservice.dto.PaymentRequestDto;
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
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void walletBalance_walletExists_returnsBalanceForSpecialist() {
        Long walletId = 1L;
        BigDecimal balance = BigDecimal.valueOf(1000);

        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setBalance(balance);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        BigDecimal result = walletService.walletBalanceForSpecialist(/*walletId*/);

        assertEquals(balance, result);
        verify(walletRepository).findById(walletId);
    }

    @Test
    void walletBalance_walletNotFound_throwsNotFoundExceptionForSpecialist() {
        Long walletId = 2L;

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            walletService.walletBalanceForSpecialist(/*walletId*/);
        });

        assertEquals("Wallet not found", exception.getMessage());
        verify(walletRepository).findById(walletId);
    }

    @Test
    void testChargeWallet_ShouldIncreaseBalanceAndSaveTransaction() {
        Long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("100.00");
        BigDecimal chargeAmount = new BigDecimal("50.00");

        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setBalance(currentBalance);

        PaymentRequestDto request = new PaymentRequestDto();
        request.setWalletId(walletId);
        request.setAmount(chargeAmount);

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        walletService.chargeWallet(request);

        assertEquals(new BigDecimal("150.00"), wallet.getBalance());

        verify(walletRepository).save(wallet);
        verify(transactionService).save(argThat(transaction ->
                transaction.getAmount().compareTo(chargeAmount) == 0 &&
                        transaction.getType() == TransactionType.DEPOSIT &&
                        transaction.getWallet().equals(wallet)
        ));
    }
}
