package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.OrderSaveRequest;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders-of-customer")
public class OrderController {

    private final OrderService orderService;

    //✅ ok
    @PostMapping
    public ResponseEntity<OrderResponse> submitOrder(
            @RequestBody @Valid
            OrderSaveRequest request) {
        return ResponseEntity.ok(
                orderService.submitOrderForHomeService(request));
    }
}
