package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OrderRepository;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private HomeServiceService homeServiceService;
    @Mock
    private CustomerService customerService;
    @Mock
    private SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSubmitOrderForHomeService_Success() {
        OrderSaveRequest request = new OrderSaveRequest();
        request.setHomeServiceId(1L);
        request.setSuggestedPrice(BigDecimal.valueOf(150));
        request.setDescription("Test order");
        request.setStartDate(ZonedDateTime.now().plusDays(1));
        request.setAddressId(10L);

        HomeService homeService = new HomeService();
        homeService.setBasePrice(BigDecimal.valueOf(100));

        Customer customer = new Customer();
        customer.setId(5L);

        Order savedOrder = new Order();
        savedOrder.setId(100L);

        OrderResponse response = new OrderResponse();
        response.setId(100L);

        when(homeServiceService.findById(1L)).thenReturn(homeService);
        when(securityUtil.getCurrentUsername()).thenReturn("user@example.com");
        when(customerService.findByEmail("user@example.com")).thenReturn(customer);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.entityMapToResponse(savedOrder)).thenReturn(response);

        OrderResponse result = orderService.submitOrderForHomeService(request);

        assertNotNull(result);
        assertEquals(100L, result.getId());

        verify(orderRepository).save(any(Order.class));
    }



    @Test
    void submitOrderForHomeService_shouldThrowException_whenPriceIsLessThanBase() {
        OrderSaveRequest request = new OrderSaveRequest();
        request.setHomeServiceId(1L);
        request.setSuggestedPrice(BigDecimal.valueOf(50));

        HomeService homeService = new HomeService();
        homeService.setBasePrice(BigDecimal.valueOf(100));

        when(homeServiceService.findById(1L)).thenReturn(homeService);

        assertThatThrownBy(() -> orderService
                .submitOrderForHomeService(request))
                .isInstanceOf(NotValidPriceException.class)
                .hasMessageContaining("Suggested price is less than");
    }

    @Test
    void findOrderHistory_shouldReturnOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        OrderStatus status = OrderStatus.WAITING_FOR_SPECIALIST_OFFER;
        Customer customer = new Customer();
        customer.setId(1L);

        Order order = new Order();
        order.setId(1L);

        OrderResponse response = new OrderResponse();
        response.setId(1L);

        when(securityUtil.getCurrentUsername()).thenReturn("user@example.com");
        when(customerService.findByEmail("user@example.com")).thenReturn(customer);
        when(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(order)));
        when(orderMapper.entityMapToResponse(order)).thenReturn(response);

        Page<OrderResponse> result = orderService.findOrderHistory(status, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
    }



    @Test
    void orderDetailsForManager_shouldThrowNotFound_whenOrderNotExists() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService
                .orderDetailsForManager(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Order not found");
    }



    @Test
    void testOrderDetailsForManager_WhenOrderExists_ShouldReturnResponse() {
        Long orderId = 1L;
        Order order = new Order();
        OrderResponseForManager response = new OrderResponseForManager();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.entityMapToResponseForManager(order)).thenReturn(response);

        OrderResponseForManager result = orderService.orderDetailsForManager(orderId);

        assertEquals(response, result);
        verify(orderRepository).findById(orderId);
        verify(orderMapper).entityMapToResponseForManager(order);
    }


    @Test
    void testOrderDetailsForManager_WhenOrderNotFound_ShouldThrowException() {
        Long orderId = 999L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> orderService.orderDetailsForManager(orderId)
        );

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository).findById(orderId);
        verifyNoInteractions(orderMapper);
    }


    @Test
    void testOrderHistory_ShouldReturnMappedOrderSummaryPage() {
        OrderFilterRequestForManager request = new OrderFilterRequestForManager();
        Pageable pageable = PageRequest.of(0, 10);

        Order order1 = new Order();
        Order order2 = new Order();
        Page<Order> mockOrderPage = new PageImpl<>(List.of(order1, order2));

        OrderSummaryResponse response1 = new OrderSummaryResponse();
        OrderSummaryResponse response2 = new OrderSummaryResponse();

        when(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(mockOrderPage);

        when(orderMapper.entityMapToSummaryResponse(order1)).thenReturn(response1);
        when(orderMapper.entityMapToSummaryResponse(order2)).thenReturn(response2);

        Page<OrderSummaryResponse> result = orderService.orderHistory(
                request, pageable);

        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(response1));
        assertTrue(result.getContent().contains(response2));

        verify(orderRepository).findAll(any(Specification.class), eq(pageable));
        verify(orderMapper).entityMapToSummaryResponse(order1);
        verify(orderMapper).entityMapToSummaryResponse(order2);
    }


}
