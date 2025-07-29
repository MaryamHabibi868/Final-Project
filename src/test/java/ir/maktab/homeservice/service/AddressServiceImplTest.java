package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.AddressResponse;
import ir.maktab.homeservice.dto.AddressSaveRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.mapper.AddressMapper;
import ir.maktab.homeservice.repository.AddressRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddressServiceImplTest {

    private AddressRepository repository;
    private CustomerService customerService;
    private AddressMapper addressMapper;
    private SecurityUtil securityUtil;
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        repository = mock(AddressRepository.class);
        customerService = mock(CustomerService.class);
        addressMapper = mock(AddressMapper.class);
        securityUtil = mock(SecurityUtil.class);

        addressService = new AddressServiceImpl(
                repository, customerService, addressMapper, securityUtil);
    }

    @Test
    void testSubmitAddress_whenPostalCodeNotExists_shouldSaveAddress() {
        AddressSaveRequest request = new AddressSaveRequest();
        request.setProvince("Tehran");
        request.setCity("Tehran");
        request.setPostalCode("1234567890");
        request.setDescription("Test Description");

        Customer customer = new Customer();
        Address savedAddress = new Address();
        AddressResponse expectedResponse = new AddressResponse();

        when(repository.existsByPostalCode("1234567890")).thenReturn(false);
        when(securityUtil.getCurrentUsername()).thenReturn("test@example.com");
        when(customerService.findByEmail("test@example.com")).thenReturn(customer);
        when(repository.save(any(Address.class))).thenReturn(savedAddress);
        when(addressMapper.entityMapToResponse(savedAddress))
                .thenReturn(expectedResponse);

        AddressResponse actualResponse = addressService.submitAddress(request);

        assertEquals(expectedResponse, actualResponse);

        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(repository).save(addressCaptor.capture());
        Address captured = addressCaptor.getValue();
        assertEquals("Tehran", captured.getProvince());
        assertEquals("Tehran", captured.getCity());
        assertEquals("1234567890", captured.getPostalCode());
        assertEquals("Test Description", captured.getDescription());
        assertEquals(customer, captured.getCustomer());
    }

    @Test
    void testSubmitAddress_whenPostalCodeExists_shouldThrowDuplicatedException() {
        AddressSaveRequest request = new AddressSaveRequest();
        request.setPostalCode("1234567890");

        when(repository.existsByPostalCode("1234567890")).thenReturn(true);

        DuplicatedException exception = assertThrows(DuplicatedException.class,
                () -> addressService.submitAddress(request));

        assertEquals("Postal code already exists", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
