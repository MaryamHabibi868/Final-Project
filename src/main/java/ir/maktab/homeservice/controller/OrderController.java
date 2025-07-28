package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderResponse> submitOrder(
            @RequestBody @Valid
            OrderSaveRequest request) {
        return ResponseEntity.ok(
                orderService.submitOrderForHomeService(request));
    }

    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/filter-by-customer-id")
    public ResponseEntity<Page<OrderResponse>> findOrderHistory(
           /* @PathVariable Long customerId,*/
            @RequestParam(required = false) OrderStatus orderStatus,
            @PageableDefault(size = 10, sort = "createDate",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(
                orderService.findOrderHistory(/*customerId,*/ orderStatus, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/filter-order-history")
    public ResponseEntity<Page<OrderSummaryResponse>> filterOrdersForManager(
            @RequestBody OrderFilterRequestForManager request,
            @PageableDefault(size = 10, sort = "createDate",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(orderService.orderHistory(request, pageable));
    }

    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/filter-order-detail/{orderId}")
    public ResponseEntity<OrderResponseForManager> getOrderDetails(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.orderDetailsForManager(orderId));
    }
}
