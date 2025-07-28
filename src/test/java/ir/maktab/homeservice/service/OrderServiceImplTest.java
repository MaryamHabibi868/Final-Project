package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OrderRepository;
import ir.maktab.homeservice.repository.specification.OrderSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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

    @Mock
    private CustomerService customerService;

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
        /*request.setCustomerId(2L);*/
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

    @Test
    void findOrderHistory_ShouldReturnMappedPage() {
        Long customerId = 1L;
        OrderStatus orderStatus = OrderStatus.DONE;
        Pageable pageable = PageRequest.of(0, 10);

        Customer mockCustomer = new Customer();
        when(customerService.findById(customerId)).thenReturn(mockCustomer);

        Order order = new Order();
        List<Order> orders = List.of(order);
        Page<Order> orderPage = new PageImpl<>(orders, pageable, orders.size());

        when(repository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(orderPage);

        when(orderMapper.entityMapToResponse(any(Order.class)))
                .thenReturn(new OrderResponse());

        Page<OrderResponse> result = orderService
                .findOrderHistory(/*customerId,*/ orderStatus, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(customerService).findById(customerId);
        verify(repository).findAll(any(Specification.class), eq(pageable));
        verify(orderMapper).entityMapToResponse(any(Order.class));
    }


    @Test
    void orderHistory_ShouldReturnMappedSummaryPage() {
        Pageable pageable = PageRequest.of(0, 10);
        OrderFilterRequestForManager request = new OrderFilterRequestForManager();

        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = List.of(order1, order2);
        Page<Order> orderPage = new PageImpl<>(orders, pageable, orders.size());

        when(repository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(orderPage);

        OrderSummaryResponse summary1 = new OrderSummaryResponse();
        OrderSummaryResponse summary2 = new OrderSummaryResponse();

        when(orderMapper.entityMapToSummaryResponse(order1)).thenReturn(summary1);
        when(orderMapper.entityMapToSummaryResponse(order2)).thenReturn(summary2);

        Page<OrderSummaryResponse> result = orderService.orderHistory(request, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertSame(summary1, result.getContent().get(0));
        assertSame(summary2, result.getContent().get(1));

        verify(repository, times(1))
                .findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void orderDetailsForManager_WhenOrderExists_ShouldReturnResponse() {
        Long orderId = 1L;
        Order order = new Order();
        when(repository.findById(orderId)).thenReturn(Optional.of(order));

        OrderResponseForManager response = new OrderResponseForManager();
        when(orderMapper.entityMapToResponseForManager(order)).thenReturn(response);

        OrderResponseForManager result = orderService.orderDetailsForManager(orderId);

        assertNotNull(result);
        assertSame(response, result);

        verify(repository, times(1)).findById(orderId);
    }

    @Test
    void orderDetailsForManager_WhenOrderNotFound_ShouldThrow() {
        Long orderId = 1L;
        when(repository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                orderService.orderDetailsForManager(orderId));

        verify(repository, times(1)).findById(orderId);
    }
}
