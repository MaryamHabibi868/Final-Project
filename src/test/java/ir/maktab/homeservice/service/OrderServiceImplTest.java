package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private OrderService service;
    private OrderResponse response;
    private OrderSaveRequest request;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(OrderService.class);
        response = Mockito.mock(OrderResponse.class);
        request = Mockito.mock(OrderSaveRequest.class);
    }


    @Test
    void submitOrderForHomeService() {
    Mockito.when(service.submitOrderForHomeService(request)).thenReturn(response);
    assertEquals(response, service.submitOrderForHomeService(request));
    }
}