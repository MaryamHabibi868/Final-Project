package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService
        extends BaseService<Order, Long> {


    OrderResponse submitOrderForHomeService(OrderSaveRequest request);

    Page<OrderResponse> findOrderHistory(
            Long customerId, OrderStatus orderStatus, Pageable pageable);
}
