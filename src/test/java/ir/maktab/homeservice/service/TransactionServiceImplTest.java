package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByWalletId_returnsPageOfTransactions() {
        Long walletId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Transaction transaction = new Transaction();
        transaction.setId(1L);


        Page<Transaction> page = new PageImpl<>(
                Collections.singletonList(transaction));

        when(transactionRepository.findAllByWalletId(walletId,
                pageable)).thenReturn(page);

        Page<Transaction> result = transactionService
                .findAllByWalletId(walletId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(transaction, result.getContent().get(0));

        verify(transactionRepository, times(1))
                .findAllByWalletId(walletId, pageable);
    }
}
