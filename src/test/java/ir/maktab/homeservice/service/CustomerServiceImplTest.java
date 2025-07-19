/*
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
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCustomer_shouldSaveNewCustomer() {
        CustomerSaveRequest request = new CustomerSaveRequest("Niki", "Habibi", "niki@test.com", "123");
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName("Niki");
        savedCustomer.setLastName("Habibi");
        savedCustomer.setEmail("niki@test.com");
        savedCustomer.setPassword("123");
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        savedCustomer.setWallet(wallet);

        when(customerRepository.existsByEmail("niki@test.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.entityMapToResponse(savedCustomer)).thenReturn(new CustomerResponse(1L, "Niki", "Habibi", "niki@test.com"));

        CustomerResponse response = customerService.registerCustomer(request);

        assertEquals("Niki", response.getFirstName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void registerCustomer_shouldThrowException_whenEmailExists() {
        CustomerSaveRequest request = new CustomerSaveRequest("Niki", "Habibi", "niki@test.com", "123");

        when(customerRepository.existsByEmail("niki@test.com")).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> customerService.registerCustomer(request));
    }

    @Test
    void loginCustomer_shouldReturnCustomerResponse_whenValidCredentials() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("Ali");
        customer.setEmail("ali@test.com");
        customer.setPassword("123");

        when(customerRepository.findByEmailAndPassword("ali@test.com", "123")).thenReturn(Optional.of(customer));
        when(customerMapper.entityMapToResponse(customer)).thenReturn(new CustomerResponse(2L, "Ali", null, "ali@test.com"));

        CustomerLoginRequest request = new CustomerLoginRequest("ali@test.com", "123");
        CustomerResponse response = customerService.loginCustomer(request);

        assertEquals("Ali", response.getFirstName());
    }

    @Test
    void loginCustomer_shouldThrowException_whenNotFound() {
        when(customerRepository.findByEmailAndPassword("none@test.com", "123")).thenReturn(Optional.empty());

        CustomerLoginRequest request = new CustomerLoginRequest("none@test.com", "123");
        assertThrows(NotFoundException.class, () -> customerService.loginCustomer(request));
    }

    @Test
    void updateCustomer_shouldUpdateFields() {
        Customer existing = new Customer();
        existing.setId(5L);
        existing.setFirstName("Sara");
        existing.setEmail("sara@test.com");

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(5L, "SaraNew", null, "new@test.com", null);

        when(customerRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(customerRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(existing);
        when(customerMapper.entityMapToResponse(existing)).thenReturn(new CustomerResponse(5L, "SaraNew", null, "new@test.com"));

        CustomerResponse response = customerService.updateCustomer(updateRequest);

        assertEquals("SaraNew", response.getFirstName());
        assertEquals("new@test.com", response.getEmail());
    }

    @Test
    void updateCustomer_shouldThrowException_whenEmailExists() {
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(10L, "Ali", "Mohammadi", "exist@test.com", "123");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(10L);

        when(customerRepository.findById(10L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail("exist@test.com")).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> customerService.updateCustomer(updateRequest));
    }

    @Test
    void findAllByFirstNameContainsIgnoreCaseOrderByIdAsc_shouldReturnList() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ali");

        when(customerRepository.findAllByFirstNameContainsIgnoreCaseOrderByIdAsc("ali"))
                .thenReturn(List.of(customer));
        when(customerMapper.entityMapToResponse(customer))
                .thenReturn(new CustomerResponse(1L, "Ali", null, null));

        List<CustomerResponse> result = customerService.findAllByFirstNameContainsIgnoreCaseOrderByIdAsc("ali");

        assertEquals(1, result.size());
        assertEquals("Ali", result.get(0).getFirstName());
    }

    @Test
    void findAllByLastNameContainsIgnoreCaseOrderByIdAsc_shouldReturnList() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Rahimi");

        when(customerRepository.findAllByLastNameContainsIgnoreCaseOrderByIdAsc("rahimi"))
                .thenReturn(List.of(customer));
        when(customerMapper.entityMapToResponse(customer))
                .thenReturn(new CustomerResponse(1L, null, "Rahimi", null));

        List<CustomerResponse> result = customerService.findAllByLastNameContainsIgnoreCaseOrderByIdAsc("rahimi");

        assertEquals(1, result.size());
        assertEquals("Rahimi", result.get(0).getLastName());
    }
}
*/
