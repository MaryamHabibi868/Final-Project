package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.AddressResponse;
import ir.maktab.homeservice.dto.AddressSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceImplTest {

    private AddressService service;
    private AddressResponse response;
    private AddressSaveRequest request;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(AddressService.class);
        response = Mockito.mock(AddressResponse.class);
        request = Mockito.mock(AddressSaveRequest.class);
    }


    @Test
    void submitAddress() {
        Mockito.when(service.submitAddress(request)).thenReturn(response);
        assertEquals(response, service.submitAddress(request));
    }
}