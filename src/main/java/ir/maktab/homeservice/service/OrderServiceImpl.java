package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderMapper;
import ir.maktab.homeservice.repository.OrderRepository;
import ir.maktab.homeservice.repository.specification.OrderSpecification;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl
        extends BaseServiceImpl<Order, Long, OrderRepository>
        implements OrderService {
    private final OrderMapper orderMapper;
    private final HomeServiceService homeServiceService;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository repository,
                            OrderMapper orderMapper,
                            HomeServiceService homeServiceService, CustomerService customerService) {
        super(repository);
        this.orderMapper = orderMapper;
        this.homeServiceService = homeServiceService;
        this.customerService = customerService;
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
        order.setDescription(request.getDescription());
        order.setSuggestedPrice(request.getSuggestedPrice());
        order.setStartDate(request.getStartDate());
        order.setAddress(Address.builder().id(request.getAddressId()).build());
        order.setCustomer(Customer.builder().id(request.getCustomerId()).build());
        order.setHomeService(homeServiceFound);
        order.setStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);
        Order save = repository.save(order);
        return orderMapper.entityMapToResponse(save);
    }

    @Override
    public Page<OrderResponse> findOrderHistory(
            Long customerId, OrderStatus orderStatus, Pageable pageable) {
        customerService.findById(customerId);

        Specification<Order> spec = OrderSpecification.hasCustomerId(customerId)
                .and(orderStatus != null
                        ? OrderSpecification.hasStatus(orderStatus) : null);

        return repository.findAll(spec, pageable)
                        .map(orderMapper::entityMapToResponse);
    }
}
