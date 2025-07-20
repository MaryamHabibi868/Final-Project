package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.domains.enumClasses.OrderStatus;
import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> submitOrder(
            @RequestBody @Valid
            OrderSaveRequest request) {
        return ResponseEntity.ok(
                orderService.submitOrderForHomeService(request));
    }

    @GetMapping("/filter-by-customer-id/{customerId}")
    public ResponseEntity<Page<OrderResponse>> findOrderHistory(
            @PathVariable Long customerId,
            @RequestParam (required = false) OrderStatus orderStatus,
            @PageableDefault(size = 10, sort = "createDate",
                    direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(
                orderService.findOrderHistory(customerId, orderStatus, pageable));
    }
}
