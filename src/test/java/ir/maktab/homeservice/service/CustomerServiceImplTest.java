package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.CustomerLoginRequest;
import ir.maktab.homeservice.dto.CustomerResponse;
import ir.maktab.homeservice.dto.CustomerSaveRequest;
import ir.maktab.homeservice.dto.CustomerUpdateRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCustomer_successful() {
        // arrange
        CustomerSaveRequest request = new CustomerSaveRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("pass123");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        when(customerRepository.save(customerCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        CustomerResponse mockResponse = new CustomerResponse();
        when(customerMapper.entityMapToResponse(any(Customer.class))).thenReturn(mockResponse);

        // act
        CustomerResponse result = customerService.registerCustomer(request);

        // assert
        assertNotNull(result);
        Customer savedCustomer = customerCaptor.getValue();
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("encodedPassword", savedCustomer.getPassword());
        verify(verificationTokenService).save(any());
    }

    @Test
    void registerCustomer_duplicateEmailAndVerified_throwsException() {
        // arrange
        CustomerSaveRequest request = new CustomerSaveRequest();
        request.setEmail("existing@example.com");

        Customer existing = new Customer();
        existing.setIsEmailVerify(true);
        when(customerRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existing));

        // act & assert
        assertThrows(DuplicatedException.class, () -> customerService.registerCustomer(request));
    }

    // ✅ Test: updateCustomer - Success
    @Test
    void updateCustomer_shouldUpdateAndReturnCustomerResponse() {
        String currentEmail = "old@example.com";
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setEmail(currentEmail);
        existingCustomer.setFirstName("Old");
        existingCustomer.setLastName("User");

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setEmail("new@example.com");
        request.setFirstName("New");
        request.setLastName("Name");
        request.setPassword("rawpass");

        Customer savedCustomer = new Customer();
        savedCustomer.setEmail(request.getEmail());
        savedCustomer.setFirstName(request.getFirstName());
        savedCustomer.setLastName(request.getLastName());

        CustomerResponse mockResponse = new CustomerResponse();
        mockResponse.setEmail(request.getEmail());

        when(securityUtil.getCurrentUsername()).thenReturn(currentEmail);
        when(customerRepository.findByEmail(currentEmail)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("rawpass")).thenReturn("encodedpass");
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.entityMapToResponse(savedCustomer)).thenReturn(mockResponse);

        CustomerResponse result = customerService.updateCustomer(request);

        assertEquals("new@example.com", result.getEmail());
        verify(customerRepository).save(existingCustomer);
    }

    // ❌ Test: updateCustomer - Not Found
    @Test
    void updateCustomer_shouldThrowNotFoundException_whenCustomerNotFound() {
        when(securityUtil.getCurrentUsername()).thenReturn("notfound@example.com");
        when(customerRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        assertThrows(NotFoundException.class, () -> customerService.updateCustomer(request));
    }

    // ❌ Test: updateCustomer - Duplicate Email
    @Test
    void updateCustomer_shouldThrowDuplicatedException_whenEmailExists() {
        Customer existingCustomer = new Customer();
        existingCustomer.setEmail("old@example.com");

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setEmail("duplicate@example.com");

        when(securityUtil.getCurrentUsername()).thenReturn("old@example.com");
        when(customerRepository.findByEmail("old@example.com")).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> customerService.updateCustomer(request));
    }

    // ✅ Test: loginCustomer - Success
    @Test
    void loginCustomer_shouldReturnCustomerResponse_whenCredentialsAreCorrect() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");

        CustomerLoginRequest loginRequest = new CustomerLoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        CustomerResponse response = new CustomerResponse();
        response.setEmail("test@example.com");

        when(customerRepository.findByEmailAndPassword("test@example.com", "password")).thenReturn(Optional.of(customer));
        when(customerMapper.entityMapToResponse(customer)).thenReturn(response);

        CustomerResponse result = customerService.loginCustomer(loginRequest);

        assertEquals("test@example.com", result.getEmail());
    }

    // ❌ Test: loginCustomer - Not Found
    @Test
    void loginCustomer_shouldThrowNotFoundException_whenCredentialsIncorrect() {
        CustomerLoginRequest loginRequest = new CustomerLoginRequest();
        loginRequest.setEmail("wrong@example.com");
        loginRequest.setPassword("wrongpass");

        when(customerRepository.findByEmailAndPassword("wrong@example.com", "wrongpass")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.loginCustomer(loginRequest));
    }

    @Test
    void findByEmail_notFound_throwsException() {
        when(customerRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerService.findByEmail("notfound@example.com"));
    }
}
