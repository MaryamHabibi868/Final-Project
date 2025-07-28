package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.exception.NotFoundException;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OrderRepository;
import ir.maktab.homeservice.repository.specification.OrderSpecification;
import ir.maktab.homeservice.repository.specification.OrderSpecificationForManager;
import ir.maktab.homeservice.security.SecurityUtil;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl
        extends BaseServiceImpl<Order, Long, OrderRepository>
        implements OrderService {
    private final OrderMapper orderMapper;
    private final HomeServiceService homeServiceService;
    private final CustomerService customerService;
    private final SecurityUtil securityUtil;

    public OrderServiceImpl(OrderRepository repository,
                            OrderMapper orderMapper,
                            HomeServiceService homeServiceService,
                            CustomerService customerService,
                            SecurityUtil securityUtil) {
        super(repository);
        this.orderMapper = orderMapper;
        this.homeServiceService = homeServiceService;
        this.customerService = customerService;
        this.securityUtil = securityUtil;
    }


    @Transactional
    @Override
    public OrderResponse submitOrderForHomeService(
            OrderSaveRequest request) {

        HomeService homeServiceFound = homeServiceService
                .findById(request.getHomeServiceId());

        if (request.getSuggestedPrice().compareTo(homeServiceFound.getBasePrice()) < 0) {
            throw new NotValidPriceException(
                    "Suggested price is less than the base price of this home service");
        }
        Order order = new Order();

        String email = securityUtil.getCurrentUsername();
        Customer byEmail = customerService.findByEmail(email);

        order.setDescription(request.getDescription());
        order.setSuggestedPrice(request.getSuggestedPrice());
        order.setStartDate(request.getStartDate());
        order.setAddress(Address.builder().id(request.getAddressId()).build());
        order.setCustomer(byEmail);
        order.setHomeService(homeServiceFound);
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);
        Order save = repository.save(order);
        return orderMapper.entityMapToResponse(save);
    }

    @Override
    public Page<OrderResponse> findOrderHistory(
           /* Long customerId,*/ OrderStatus orderStatus, Pageable pageable) {
        /*customerService.findById(customerId);*/

        String email = securityUtil.getCurrentUsername();
        Customer customer = customerService.findByEmail(email);

        Long customerId = customer.getId();

        Specification<Order> spec = OrderSpecification.hasCustomerId(customerId)
                .and(orderStatus != null
                        ? OrderSpecification.hasStatus(orderStatus) : null);

        return repository.findAll(spec, pageable)
                .map(orderMapper::entityMapToResponse);
    }

    @Override
    public Page<OrderSummaryResponse> orderHistory(
            OrderFilterRequestForManager request, Pageable pageable) {
        Page<Order> orders = repository.findAll(
                OrderSpecificationForManager.withFilters(request), pageable);
        return orders.map(orderMapper::entityMapToSummaryResponse);
    }


    @Override
    public OrderResponseForManager orderDetailsForManager(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        return orderMapper.entityMapToResponseForManager(order);
    }
}
