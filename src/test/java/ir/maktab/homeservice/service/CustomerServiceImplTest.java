package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.CustomerLoginRequest;
import ir.maktab.homeservice.dto.CustomerResponse;
import ir.maktab.homeservice.dto.CustomerSaveRequest;
import ir.maktab.homeservice.dto.CustomerUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    private CustomerService service;
    private CustomerResponse response;
    private CustomerSaveRequest saveRequest;
    private CustomerUpdateRequest updateRequest;
    private CustomerLoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(CustomerService.class);
        response = Mockito.mock(CustomerResponse.class);
        saveRequest = Mockito.mock(CustomerSaveRequest.class);
        updateRequest = Mockito.mock(CustomerUpdateRequest.class);
        loginRequest = Mockito.mock(CustomerLoginRequest.class);
    }

    @Test
    void registerCustomer() {
        Mockito.when(service.registerCustomer(saveRequest)).thenReturn(response);
        assertEquals(response, service.registerCustomer(saveRequest));
    }

    @Test
    void updateCustomer() {
        Mockito.when(service.updateCustomer(updateRequest)).thenReturn(response);
        assertEquals(response, service.updateCustomer(updateRequest));
    }

    @Test
    void loginCustomer() {
        Mockito.when(service.loginCustomer(loginRequest)).thenReturn(response);
        assertEquals(response, service.loginCustomer(loginRequest));

    }

    @Test
    void findAllCustomers() {
        Mockito.when(service.findAllCustomers())
                .thenReturn(List.of(response));
        assertEquals(List.of(response), service.findAllCustomers());
    }

    @Test
    void findAllByFirstNameContainsIgnoreCaseOrderByIdAsc() {
        Mockito.when(service
                        .findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
                                (Mockito.anyString()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response),
                service.findAllByFirstNameContainsIgnoreCaseOrderByIdAsc
                        (Mockito.anyString()));
    }

    @Test
    void findAllByLastNameContainsIgnoreCaseOrderByIdAsc() {
        Mockito.when(service
                        .findAllByLastNameContainsIgnoreCaseOrderByIdAsc
                                (Mockito.anyString()))
                .thenReturn(List.of(response));
        assertEquals(List.of(response),
                service.findAllByLastNameContainsIgnoreCaseOrderByIdAsc
                        (Mockito.anyString()));
    }
}