package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
import ir.maktab.homeservice.service.base.BaseService;

public interface OrderOfCustomerService
        extends BaseService<OrderOfCustomer, Long> {

    OrderOfCustomerRequest submitOrder(OrderOfCustomerRequest request);
}
