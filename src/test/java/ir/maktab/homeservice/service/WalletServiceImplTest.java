package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.service.WalletServiceImpl;
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
}
