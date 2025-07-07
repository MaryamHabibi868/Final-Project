package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.domains.OrderStatus;
import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
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

    public OrderOfCustomerServiceImpl(OrderOfCustomerRepository repository,
                                      OrderOfCustomerMapper orderOfCustomerMapper) {
        super(repository);
        this.orderOfCustomerMapper = orderOfCustomerMapper;
    }

    @Override
    public OrderOfCustomerRequest submitOrder(OrderOfCustomerRequest request) {
        OrderOfCustomer orderOfCustomer1 = orderOfCustomerMapper.orderOfCustomerDTOMapToEntity(request);
        if (orderOfCustomer1.getHomeService().getBasePrice().compareTo(request.getSuggestedPrice()) < 0) {
            throw new NotValidPriceException(
                    "The suggested price is less than the base price of this home service");
        }
            OrderOfCustomer orderOfCustomer = new OrderOfCustomer();
            orderOfCustomer.setDescription(request.getDescription());
            orderOfCustomer.setSuggestedPrice(request.getSuggestedPrice());
            orderOfCustomer.setStartDate(orderOfCustomer.getStartDate());
            orderOfCustomer.setAddress(orderOfCustomer.getAddress());
            orderOfCustomer.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
            OrderOfCustomer save = repository.save(orderOfCustomer);
            return orderOfCustomerMapper.orderOfCustomerMapToDTO(save);
    }
}
