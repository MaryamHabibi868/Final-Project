package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Specialist;
import ir.maktab.homeservice.domains.Transaction;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.PaymentRequestDto;
import ir.maktab.homeservice.dto.WalletResponse;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.WalletMapper;
import ir.maktab.homeservice.repository.WalletRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletRepository repository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private SpecialistService specialistService;

    @Mock
    private CustomerService customerService;

    @Mock
    private WalletMapper walletMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void testWalletBalanceForSpecialist_Success() {
        String email = "specialist@example.com";

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.valueOf(500));

        Specialist specialist = new Specialist();
        specialist.setWallet(wallet);

        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setBalance(wallet.getBalance());

        when(securityUtil.getCurrentUsername()).thenReturn(email);
        when(specialistService.findByEmail(email))
                .thenReturn(specialist);
        when(repository.findById(wallet.getId()))
                .thenReturn(Optional.of(wallet));
        when(walletMapper.entityMapToWalletResponse(wallet))
                .thenReturn(walletResponse);

        WalletResponse response = walletService.walletBalanceForSpecialist();

        assertNotNull(response);
        assertEquals(wallet.getBalance(), response.getBalance());

        verify(securityUtil).getCurrentUsername();
        verify(specialistService).findByEmail(email);
        verify(repository).findById(wallet.getId());
        verify(walletMapper).entityMapToWalletResponse(wallet);
    }

    @Test
    void testWalletBalanceForSpecialist_WalletNotFound() {
        String email = "specialist@example.com";

        Wallet wallet = new Wallet();
        wallet.setId(1L);

        Specialist specialist = new Specialist();
        specialist.setWallet(wallet);

        when(securityUtil.getCurrentUsername()).thenReturn(email);
        when(specialistService.findByEmail(email)).thenReturn(specialist);
        when(repository.findById(wallet.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> walletService.walletBalanceForSpecialist());

        verify(repository).findById(wallet.getId());
    }

    @Test
    void testChargeWallet_Success() {
        PaymentRequestDto request = new PaymentRequestDto();
        request.setWalletId(1L);
        request.setAmount(BigDecimal.valueOf(100));

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.valueOf(200));

        when(repository.findById(request.getWalletId()))
                .thenReturn(Optional.of(wallet));
        when(repository.save(any(Wallet.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        walletService.chargeWallet(request);

        assertEquals(BigDecimal.valueOf(300), wallet.getBalance());

        verify(repository).save(wallet);

        verify(transactionService).save(any(Transaction.class));
    }

    @Test
    void testWalletBalanceForCustomer_Success() {
        String email = "customer@example.com";

        Wallet wallet = new Wallet();
        wallet.setId(2L);
        wallet.setBalance(BigDecimal.valueOf(700));

        Customer customer = new Customer();
        customer.setWallet(wallet);

        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setBalance(wallet.getBalance());

        when(securityUtil.getCurrentUsername())
                .thenReturn(email);
        when(customerService.findByEmail(email))
                .thenReturn(customer);
        when(repository.findById(wallet.getId()))
                .thenReturn(Optional.of(wallet));
        when(walletMapper.entityMapToWalletResponse(wallet))
                .thenReturn(walletResponse);

        WalletResponse response = walletService.walletBalanceForCustomer();

        assertNotNull(response);
        assertEquals(wallet.getBalance(), response.getBalance());

        verify(securityUtil).getCurrentUsername();
        verify(customerService).findByEmail(email);
        verify(repository).findById(wallet.getId());
        verify(walletMapper).entityMapToWalletResponse(wallet);
    }

    @Test
    void testWalletBalanceForCustomer_WalletNotFound() {
        String email = "customer@example.com";

        Wallet wallet = new Wallet();
        wallet.setId(2L);

        Customer customer = new Customer();
        customer.setWallet(wallet);

        when(securityUtil.getCurrentUsername())
                .thenReturn(email);
        when(customerService.findByEmail(email))
                .thenReturn(customer);
        when(repository.findById(wallet.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> walletService.walletBalanceForCustomer());

        verify(repository).findById(wallet.getId());
    }
}
