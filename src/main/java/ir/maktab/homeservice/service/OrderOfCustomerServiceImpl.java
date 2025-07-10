package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Address;
import ir.maktab.homeservice.domains.Customer;
import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
import ir.maktab.homeservice.dto.OrderOfCustomerResponse;
import ir.maktab.homeservice.exception.NotValidPriceException;
import ir.maktab.homeservice.mapper.OrderOfCustomerMapper;
import ir.maktab.homeservice.repository.OrderOfCustomerRepository;
import ir.maktab.homeservice.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderOfCustomerServiceImpl
        extends BaseServiceImpl<OrderOfCustomer, Long, OrderOfCustomerRepository>
        implements OrderOfCustomerService {
    private final OrderOfCustomerMapper orderOfCustomerMapper;
    private final HomeServiceService homeServiceService;

    public OrderOfCustomerServiceImpl(OrderOfCustomerRepository repository,
                                      OrderOfCustomerMapper orderOfCustomerMapper,
                                      HomeServiceService homeServiceService) {
        super(repository);
        this.orderOfCustomerMapper = orderOfCustomerMapper;
        this.homeServiceService = homeServiceService;
    }

    //✅
    @Override
    public OrderOfCustomerResponse submitOrderForHomeService(
            OrderOfCustomerRequest request) {

        HomeService homeServiceFound = homeServiceService
                .findById(request.getHomeServiceId());

        if (request.getSuggestedPrice().compareTo(homeServiceFound.getBasePrice()) < 0) {
            throw new NotValidPriceException(
                    "Suggested price is less than the base price of this home service");
        }
        OrderOfCustomer orderOfCustomer = new OrderOfCustomer();
        orderOfCustomer.setDescription(request.getDescription());
        orderOfCustomer.setSuggestedPrice(request.getSuggestedPrice());
        orderOfCustomer.setStartDate(request.getStartDate());
        orderOfCustomer.setAddress(Address.builder().id(request.getAddressId()).build());
        orderOfCustomer.setCustomer(Customer.builder().id(request.getCustomerId()).build());
        orderOfCustomer.setHomeService(homeServiceFound);
        orderOfCustomer.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);
        OrderOfCustomer save = repository.save(orderOfCustomer);
        return orderOfCustomerMapper.entityMapToResponse(save);
    }
}
