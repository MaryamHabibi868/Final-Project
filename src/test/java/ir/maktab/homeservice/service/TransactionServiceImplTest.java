package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceImplTest {

    private TransactionService service;
    private TransactionResponse response;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(TransactionService.class);
        response = Mockito.mock(TransactionResponse.class);
    }


    @Test
    void findAllForSpecialist() {
    Mockito.when(service.findAllForSpecialist(Mockito.anyLong()))
            .thenReturn(List.of(response));
    assertEquals(List.of(response), service.findAllForSpecialist(Mockito.anyLong()));
    }
}