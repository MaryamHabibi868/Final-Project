package ir.maktab.homeservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletServiceImplTest {

    private WalletService service;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(WalletService.class);
    }


    @Test
    void walletBalance() {
    Mockito.when(service.walletBalance(Mockito.anyLong()))
            .thenReturn(BigDecimal.valueOf(10.00));
    assertEquals(BigDecimal.valueOf(10.00), service.walletBalance(Mockito.anyLong()));

    }
}