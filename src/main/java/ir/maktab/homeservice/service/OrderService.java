package ir.maktab.homeservice.service;

import ir.maktab.homeservice.domains.Order;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.base.BaseService;

public interface OrderService
        extends BaseService<Order, Long> {

    // ☑️ final check
    //✅
    OrderResponse submitOrderForHomeService(OrderSaveRequest request);
}
