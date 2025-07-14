package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceImplTest {

    private TransactionService service;
    private Transaction response;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(TransactionService.class);
        response = Mockito.mock(Transaction.class);
    }


    @Test
    void findAllByWalletId() {
    Mockito.when(service.findAllByWalletId(Mockito.anyLong()))
            .thenReturn(List.of(response));
    assertEquals(List.of(response), service.findAllByWalletId(Mockito.anyLong()));
    }
}