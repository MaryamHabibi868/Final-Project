package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.VerificationToken;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityUtil securityUtil;
    @Mock
    private VerificationTokenService verificationTokenService;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCustomer_shouldRegisterAndSendVerificationEmail() {
        CustomerSaveRequest request = new CustomerSaveRequest(
                "John", "Doe", "john@example.com",
                "password");
        when(customerRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(i -> i.getArgument(0));
        when(customerMapper.entityMapToResponse(any()))
                .thenReturn(new CustomerResponse());

        CustomerResponse response = customerService.registerCustomer(request);

        assertNotNull(response);
        verify(emailService).sendVerificationEmail(
                eq("john@example.com"), any());
    }

    @Test
    void registerCustomer_shouldThrowExceptionIfEmailVerifiedExists() {
        Customer customer = new Customer();
        customer.setIsEmailVerify(true);
        when(customerRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(customer));

        CustomerSaveRequest request = new CustomerSaveRequest(
                "John", "Doe", "john@example.com",
                "password");

        assertThrows(DuplicatedException.class,
                () -> customerService.registerCustomer(request));
    }

    @Test
    void sendVerificationEmail_shouldGenerateTokenAndSendEmail() {
        Customer customer = new Customer();
        customer.setEmail("john@example.com");

        customerService.sendVerificationEmail(customer);

        verify(verificationTokenService).save(any(VerificationToken.class));
        verify(emailService).sendVerificationEmail(
                eq("john@example.com"), any());
    }

    @Test
    void verifyCustomerEmail_shouldVerifyAndReturnResponse() {
        String token = UUID.randomUUID().toString();
        Customer customer = new Customer();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(customer);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        when(verificationTokenService.findByToken(token))
                .thenReturn(Optional.of(verificationToken));
        when(customerRepository.save(any())).thenReturn(customer);
        when(verificationTokenService.save(any()))
                .thenReturn(verificationToken);
        when(customerMapper.entityMapToVerifiedUserResponse(any()))
                .thenReturn(new VerifiedUserResponse());

        VerifiedUserResponse response = customerService.verifyCustomerEmail(token);

        assertNotNull(response);
    }

    @Test
    void verifyCustomerEmail_shouldThrowIfTokenUsed() {
        VerificationToken token = new VerificationToken();
        token.setUsed(true);
        when(verificationTokenService.findByToken("token"))
                .thenReturn(Optional.of(token));

        assertThrows(IllegalStateException.class,
                () -> customerService.verifyCustomerEmail("token"));
    }

    @Test
    void verifyCustomerEmail_shouldThrowIfTokenExpired() {
        VerificationToken token = new VerificationToken();
        token.setUsed(false);
        token.setExpiryDate(LocalDateTime.now().minusHours(1));
        when(verificationTokenService.findByToken("token"))
                .thenReturn(Optional.of(token));

        assertThrows(IllegalStateException.class,
                () -> customerService.verifyCustomerEmail("token"));
    }

    @Test
    void verifyCustomerEmail_shouldThrowIfTokenNotFound() {
        when(verificationTokenService.findByToken("invalidToken"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> customerService.verifyCustomerEmail("invalidToken"));
    }

    @Test
    void updateCustomer_shouldUpdateFields() {
        Customer customer = new Customer();
        customer.setEmail("old@example.com");
        when(securityUtil.getCurrentUsername())
                .thenReturn("old@example.com");
        when(customerRepository.findByEmail("old@example.com"))
                .thenReturn(Optional.of(customer));
        when(customerRepository.existsByEmail("new@example.com"))
                .thenReturn(false);
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerMapper.entityMapToResponse(any()))
                .thenReturn(new CustomerResponse());

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "Ali", "Rezaei",
                "new@example.com", "1234");
        CustomerResponse response = customerService.updateCustomer(request);

        assertNotNull(response);
        verify(customerRepository).save(any());
    }

    @Test
    void updateCustomer_shouldThrowIfEmailExists() {
        Customer customer = new Customer();
        when(securityUtil.getCurrentUsername())
                .thenReturn("user@example.com");
        when(customerRepository.findByEmail("user@example.com"))
                .thenReturn(Optional.of(customer));
        when(customerRepository.existsByEmail("taken@example.com"))
                .thenReturn(true);

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "Ali", "Rezaei",
                "taken@example.com", "1234");

        assertThrows(DuplicatedException.class,
                () -> customerService.updateCustomer(request));
    }

    @Test
    void loginCustomer_shouldReturnResponseIfFound() {
        Customer customer = new Customer();
        when(customerRepository.findByEmailAndPassword(
                "a@b.com", "1234"))
                .thenReturn(Optional.of(customer));
        when(customerMapper.entityMapToResponse(any()))
                .thenReturn(new CustomerResponse());

        CustomerLoginRequest request = new CustomerLoginRequest(
                "a@b.com", "1234");
        CustomerResponse response = customerService.loginCustomer(request);

        assertNotNull(response);
    }

    @Test
    void loginCustomer_shouldThrowIfNotFound() {
        when(customerRepository.findByEmailAndPassword(
                "a@b.com", "1234"))
                .thenReturn(Optional.empty());

        CustomerLoginRequest request = new CustomerLoginRequest(
                "a@b.com", "1234");

        assertThrows(NotFoundException.class,
                () -> customerService.loginCustomer(request));
    }

    @Test
    void findByEmail_shouldReturnCustomer() {
        Customer customer = new Customer();
        when(customerRepository.findByEmail("a@b.com"))
                .thenReturn(Optional.of(customer));

        Customer result = customerService.findByEmail("a@b.com");

        assertNotNull(result);
    }

    @Test
    void findByEmail_shouldThrowIfNotFound() {
        when(customerRepository.findByEmail("a@b.com"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> customerService.findByEmail("a@b.com"));
    }


    @Test
    void updateCustomer_shouldThrowNotFoundIfCustomerNotExists() {
        when(securityUtil.getCurrentUsername())
                .thenReturn("test@example.com");
        when(customerRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setEmail("any@example.com");

        assertThrows(NotFoundException.class,
                () -> customerService.updateCustomer(request));
    }
}
