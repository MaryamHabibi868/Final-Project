package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.Wallet;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void registerCustomer_successful() {
        CustomerSaveRequest request = new CustomerSaveRequest(
                "John", "Doe", "john@example.com", "password"
        );

        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName(request.getFirstName());
        savedCustomer.setLastName(request.getLastName());
        savedCustomer.setEmail(request.getEmail());
        savedCustomer.setPassword(request.getPassword());
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUserInformation(savedCustomer);
        savedCustomer.setWallet(wallet);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        CustomerResponse expectedResponse = new CustomerResponse(1L, "John", "Doe", "john@example.com", null);
        when(customerMapper.entityMapToResponse(savedCustomer)).thenReturn(expectedResponse);

        CustomerResponse result = customerService.registerCustomer(request);

        assertNotNull(result);
        assertEquals(expectedResponse.getEmail(), result.getEmail());
    }

    @Test
    void registerCustomer_emailExists_shouldThrowException() {
        CustomerSaveRequest request = new CustomerSaveRequest(
                "John", "Doe", "john@example.com", "password"
        );
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> customerService.registerCustomer(request));
    }

    @Test
    void updateCustomer_successful() {
        CustomerUpdateRequest request = new CustomerUpdateRequest(1L, "Maryam", "Habibi", "maryam@gmail.com", "1234");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("Old");
        existingCustomer.setLastName("Name");
        existingCustomer.setEmail("old@mail.com");
        existingCustomer.setPassword("old");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.save(any())).thenReturn(existingCustomer);

        CustomerResponse expectedResponse = new CustomerResponse(1L, "Maryam", "Habibi", "maryam@gmail.com", null);
        when(customerMapper.entityMapToResponse(existingCustomer)).thenReturn(expectedResponse);

        CustomerResponse result = customerService.updateCustomer(request);

        assertEquals("Maryam", result.getFirstName());
        assertEquals("maryam@gmail.com", result.getEmail());
    }

    @Test
    void updateCustomer_notFound_shouldThrowException() {
        CustomerUpdateRequest request = new CustomerUpdateRequest(1L, "Maryam", "Habibi", "maryam@gmail.com", "1234");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.updateCustomer(request));
    }

    @Test
    void loginCustomer_successful() {
        CustomerLoginRequest request = new CustomerLoginRequest("test@mail.com", "pass");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@mail.com");
        customer.setPassword("pass");

        when(customerRepository.findByEmailAndPassword("test@mail.com", "pass"))
                .thenReturn(Optional.of(customer));

        CustomerResponse expectedResponse = new CustomerResponse(1L, "Ali", "Rezaei", "test@mail.com", null);
        when(customerMapper.entityMapToResponse(customer)).thenReturn(expectedResponse);

        CustomerResponse result = customerService.loginCustomer(request);

        assertEquals("test@mail.com", result.getEmail());
    }

    @Test
    void loginCustomer_notFound_shouldThrowException() {
        CustomerLoginRequest request = new CustomerLoginRequest("notfound@mail.com", "wrong");

        when(customerRepository.findByEmailAndPassword(request.getEmail(), request.getPassword()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.loginCustomer(request));
    }
}
