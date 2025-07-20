package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository repository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private HomeServiceService homeServiceService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void submitOrderForHomeService_shouldReturnResponse_whenDataIsValid() {
        OrderSaveRequest request = new OrderSaveRequest();
        request.setDescription("Fix plumbing");
        request.setSuggestedPrice(BigDecimal.valueOf(200));
        request.setStartDate(ZonedDateTime.now());
        request.setAddressId(1L);
        request.setCustomerId(2L);
        request.setHomeServiceId(3L);

        HomeService homeService = new HomeService();
        homeService.setBasePrice(BigDecimal.valueOf(100));

        Order savedOrder = new Order();
        savedOrder.setId(10L);

        OrderResponse expectedResponse = new OrderResponse();
        expectedResponse.setId(10L);

        when(homeServiceService.findById(3L)).thenReturn(homeService);
        when(repository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.entityMapToResponse(savedOrder)).thenReturn(expectedResponse);

        OrderResponse response = orderService.submitOrderForHomeService(request);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        verify(repository).save(any(Order.class));
    }

    @Test
    public void submitOrderForHomeService_shouldThrowException_whenPriceIsTooLow() {
        OrderSaveRequest request = new OrderSaveRequest();
        request.setSuggestedPrice(BigDecimal.valueOf(50));
        request.setHomeServiceId(1L);

        HomeService homeService = new HomeService();
        homeService.setBasePrice(BigDecimal.valueOf(100));

        when(homeServiceService.findById(1L)).thenReturn(homeService);

        assertThrows(NotValidPriceException.class,
                () -> orderService.submitOrderForHomeService(request));
    }
}
