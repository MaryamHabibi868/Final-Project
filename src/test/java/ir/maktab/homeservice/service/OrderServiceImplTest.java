package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.*;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OrderRepository;
import ir.maktab.homeservice.repository.specification.OrderSpecification;
import ir.maktab.homeservice.repository.specification.OrderSpecificationForManager;
import ir.maktab.homeservice.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    /*@Test
    void submitOrderForHomeService_shouldSaveOrder_whenPriceIsValid() {
        OrderSaveRequest request = new OrderSaveRequest();
        request.setHomeServiceId(1L);
        request.setSuggestedPrice(BigDecimal.valueOf(200));
        request.setStartDate(ZonedDateTime.from(LocalDate.now()));
        request.setDescription("desc");
        request.setAddressId(1L);

        HomeService homeService = new HomeService();
        homeService.setId(1L);
        homeService.setBasePrice(BigDecimal.valueOf(100));

        Customer customer = new Customer();
        customer.setId(1L);

        Order savedOrder = new Order();
        savedOrder.setId(10L);

        OrderResponse response = new OrderResponse();
        response.setId(10L);

        when(homeServiceService.findById(1L)).thenReturn(homeService);
        when(securityUtil.getCurrentUsername()).thenReturn("user@example.com");
        when(customerService.findByEmail("user@example.com")).thenReturn(customer);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.entityMapToResponse(savedOrder)).thenReturn(response);

        OrderResponse result = orderService.submitOrderForHomeService(request);

        assertThat(result.getId()).isEqualTo(10L);
        verify(orderRepository).save(any(Order.class));
    }*/

    @Test
    void submitOrderForHomeService_shouldThrowException_whenPriceIsLessThanBase() {
        OrderSaveRequest request = new OrderSaveRequest();
        request.setHomeServiceId(1L);
        request.setSuggestedPrice(BigDecimal.valueOf(50));

        HomeService homeService = new HomeService();
        homeService.setBasePrice(BigDecimal.valueOf(100));

        when(homeServiceService.findById(1L)).thenReturn(homeService);

        assertThatThrownBy(() -> orderService.submitOrderForHomeService(request))
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

    /*@Test
    void orderHistory_shouldReturnSummaries() {
        Pageable pageable = PageRequest.of(0, 5);
        Order order = new Order();
        order.setId(1L);

        OrderSummaryResponse summary = new OrderSummaryResponse();
        summary.setId(1L);

        OrderFilterRequestForManager filter = new OrderFilterRequestForManager();

        when(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(order)));
        when(orderMapper.entityMapToSummaryResponse(order)).thenReturn(summary);

        Page<OrderSummaryResponse> result = orderService.orderHistory(filter, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
    }*/

    /*@Test
    void orderDetailsForManager_shouldReturnOrderDetails_whenOrderExists() {
        Order order = new Order();
        order.setId(2L);

        OrderResponseForManager response = new OrderResponseForManager();
        response.setId(2L);

        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        when(orderMapper.entityMapToResponseForManager(order)).thenReturn(response);

        OrderResponseForManager result = orderService.orderDetailsForManager(2L);

        assertThat(result.getId()).isEqualTo(2L);
    }*/

    @Test
    void orderDetailsForManager_shouldThrowNotFound_whenOrderNotExists() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.orderDetailsForManager(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Order not found");
    }

   /* @Test
    void testOrderHistory_WhenOrdersExist_ShouldReturnMappedPage() {
        // Given
        OrderFilterRequestForManager filterRequest = new OrderFilterRequestForManager();
        Order order1 = new Order();
        Order order2 = new Order();
        Page<Order> orderPage = new PageImpl<>(List.of(order1, order2));

        OrderSummaryResponse response1 = new OrderSummaryResponse();
        OrderSummaryResponse response2 = new OrderSummaryResponse();

        when(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(orderPage);
        when(orderMapper.entityMapToSummaryResponse(order1)).thenReturn(response1);
        when(orderMapper.entityMapToSummaryResponse(order2)).thenReturn(response2);

        // When
        Page<OrderSummaryResponse> result = orderService.orderHistory(filterRequest, pageable);

        // Then
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(response1));
        assertTrue(result.getContent().contains(response2));
        verify(orderRepository).findAll(any(Specification.class), eq(pageable));
        verify(orderMapper, times(2)).entityMapToSummaryResponse(any(Order.class));
    }*/

    @Test
    void testOrderDetailsForManager_WhenOrderExists_ShouldReturnResponse() {
        // Given
        Long orderId = 1L;
        Order order = new Order();
        OrderResponseForManager response = new OrderResponseForManager();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.entityMapToResponseForManager(order)).thenReturn(response);

        // When
        OrderResponseForManager result = orderService.orderDetailsForManager(orderId);

        // Then
        assertEquals(response, result);
        verify(orderRepository).findById(orderId);
        verify(orderMapper).entityMapToResponseForManager(order);
    }


    @Test
    void testOrderDetailsForManager_WhenOrderNotFound_ShouldThrowException() {
        // Given
        Long orderId = 999L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When + Then
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
        // Arrange
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

        // Act
        Page<OrderSummaryResponse> result = orderService.orderHistory(request, pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(response1));
        assertTrue(result.getContent().contains(response2));

        verify(orderRepository).findAll(any(Specification.class), eq(pageable));
        verify(orderMapper).entityMapToSummaryResponse(order1);
        verify(orderMapper).entityMapToSummaryResponse(order2);
    }


   /* @Test
    void submitOrderForHomeService_ShouldSaveOrderAndReturnResponse() {
        // Arrange
        OrderSaveRequest request = new OrderSaveRequest();
        request.setHomeServiceId(1L);
        request.setSuggestedPrice(BigDecimal.valueOf(200));
        request.setDescription("Test desc");
        request.setStartDate(ZonedDateTime.from(LocalDate.now().plusDays(3)));
        request.setAddressId(5L);

        HomeService homeService = new HomeService();
        homeService.setId(1L);
        homeService.setBasePrice(BigDecimal.valueOf(100));

        Customer customer = new Customer();
        customer.setId(10L);

        Order savedOrder = new Order();
        savedOrder.setId(99L);

        OrderResponse response = new OrderResponse();
        response.setId(99L);

        when(homeServiceService.findById(1L)).thenReturn(homeService);
        when(securityUtil.getCurrentUsername()).thenReturn("customer@example.com");
        when(customerService.findByEmail("customer@example.com")).thenReturn(customer);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderMapper.entityMapToResponse(savedOrder)).thenReturn(response);

        // Act
        OrderResponse result = orderService.submitOrderForHomeService(request);

        // Assert
        assertNotNull(result);
        assertEquals(99L, result.getId());
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).entityMapToResponse(savedOrder);
    }*/

    @Test
    void submitOrderForHomeService_ShouldThrowException_WhenPriceIsTooLow() {
        // Arrange
        OrderSaveRequest request = new OrderSaveRequest();
        request.setHomeServiceId(1L);
        request.setSuggestedPrice(BigDecimal.valueOf(50));

        HomeService homeService = new HomeService();
        homeService.setId(1L);
        homeService.setBasePrice(BigDecimal.valueOf(100));

        when(homeServiceService.findById(1L)).thenReturn(homeService);

        // Act & Assert
        assertThrows(NotValidPriceException.class,
                () -> orderService.submitOrderForHomeService(request));
    }





}
