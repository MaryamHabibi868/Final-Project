package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.exception.NotApprovedException;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.mapper.CustomerMapper;
import ir.maktab.homeservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void registerCustomer_EmailAlreadyExistsAndVerified_ThrowsDuplicatedException() {
        Customer existingCustomer = new Customer();
        existingCustomer.setIsEmailVerify(true);

        when(customerRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(existingCustomer));

        CustomerSaveRequest request = new CustomerSaveRequest();
        request.setEmail("test@example.com");

        DuplicatedException exception = assertThrows(DuplicatedException.class, () ->
                customerService.registerCustomer(request));

        assertEquals("Email address already exist", exception.getMessage());
        verify(customerRepository, never()).save(any());
    }


    @Test
    void registerCustomer_EmailExistsButNotVerified_SavesCustomer() {
        Customer existingCustomer = new Customer();
        existingCustomer.setIsEmailVerify(false);

        when(customerRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(existingCustomer));

        CustomerSaveRequest request = new CustomerSaveRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("pass");

        Customer savedCustomer = new Customer();
        savedCustomer.setEmail("test@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.entityMapToResponse(savedCustomer))
                .thenReturn(new CustomerResponse());

        CustomerResponse response = customerService.registerCustomer(request);

        assertNotNull(response);
        verify(customerRepository).save(any(Customer.class));
    }


    @Test
    void registerCustomer_NewEmail_SavesCustomer() {
        when(customerRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.empty());

        CustomerSaveRequest request = new CustomerSaveRequest();
        request.setEmail("new@example.com");
        request.setFirstName("Alice");
        request.setLastName("Smith");
        request.setPassword("secret");

        Customer savedCustomer = new Customer();
        savedCustomer.setEmail("new@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.entityMapToResponse(savedCustomer))
                .thenReturn(new CustomerResponse());

        CustomerResponse response = customerService.registerCustomer(request);

        assertNotNull(response);
        verify(customerRepository).save(any(Customer.class));
    }


    @Test
    void verifyCustomer_CustomerNotFound_ThrowsNotFoundException() {
        Customer customer = new Customer();
        customer.setEmail("notfound@example.com");

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                customerService.verifyCustomer(customer));

        assertEquals("Specialist not found", exception.getMessage());
    }


    @Test
    void verifyCustomer_AlreadyVerified_ThrowsNotApprovedException() {
        Customer customer = new Customer();
        customer.setEmail("verified@example.com");

        Customer existingCustomer = new Customer();
        existingCustomer.setIsEmailVerify(true);

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.of(existingCustomer));

        NotApprovedException exception = assertThrows(NotApprovedException.class, () ->
                customerService.verifyCustomer(customer));

        assertEquals("This specialist is already verified ", exception.getMessage());
    }


    @Test
    void verifyCustomer_SuccessfulVerification_ReturnsResponse() {
        Customer customer = new Customer();
        customer.setEmail("verifyme@example.com");

        Customer existingCustomer = new Customer();
        existingCustomer.setIsEmailVerify(false);

        when(customerRepository.findByEmail(customer.getEmail()))
                .thenReturn(Optional.of(existingCustomer));

        Customer savedCustomer = new Customer();
        savedCustomer.setEmail(customer.getEmail());
        savedCustomer.setIsEmailVerify(true);
        savedCustomer.setIsActive(true);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.entityMapToResponse(savedCustomer))
                .thenReturn(new CustomerResponse());

        CustomerResponse response = customerService.verifyCustomer(customer);

        assertNotNull(response);
        verify(customerRepository).save(any(Customer.class));
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
