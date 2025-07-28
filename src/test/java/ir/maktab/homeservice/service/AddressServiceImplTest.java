package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.dto.AddressResponse;
import ir.maktab.homeservice.dto.AddressSaveRequest;
import ir.maktab.homeservice.exception.DuplicatedException;
import ir.maktab.homeservice.mapper.AddressMapper;
import ir.maktab.homeservice.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void submitAddress_shouldSaveAddressSuccessfully() {
        AddressSaveRequest request = new AddressSaveRequest();
        request.setProvince("Tehran");
        request.setCity("Tehran");
        request.setPostalCode("12345");
        request.setDescription("My house");

        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);

        Address addressToSave = new Address();
        addressToSave.setProvince("Tehran");
        addressToSave.setCity("Tehran");
        addressToSave.setPostalCode("12345");
        addressToSave.setDescription("My house");
        addressToSave.setCustomer(mockCustomer);

        Address savedAddress = new Address();
        savedAddress.setId(100L);
        savedAddress.setProvince("Tehran");
        savedAddress.setCity("Tehran");
        savedAddress.setPostalCode("12345");
        savedAddress.setDescription("My house");
        savedAddress.setCustomer(mockCustomer);

        AddressResponse mockResponse = new AddressResponse();
        mockResponse.setId(100L);
        mockResponse.setCity("Tehran");

        when(addressRepository.existsByPostalCode("12345")).thenReturn(false);
        when(customerService.findById(1L)).thenReturn(mockCustomer);
        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);
        when(addressMapper.entityMapToResponse(savedAddress)).thenReturn(mockResponse);

        AddressResponse response = addressService.submitAddress(request);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("Tehran", response.getCity());
        verify(addressRepository).existsByPostalCode("12345");
        verify(addressRepository).save(any(Address.class));
        verify(addressMapper).entityMapToResponse(savedAddress);
    }

    @Test
    void submitAddress_shouldThrowExceptionIfPostalCodeExists() {
        AddressSaveRequest request = new AddressSaveRequest();
        request.setPostalCode("12345");

        when(addressRepository.existsByPostalCode("12345")).thenReturn(true);

        assertThrows(DuplicatedException.class, () -> addressService.submitAddress(request));
        verify(addressRepository).existsByPostalCode("12345");
        verifyNoMoreInteractions(addressRepository);
        verifyNoInteractions(customerService, addressMapper);
    }
}

