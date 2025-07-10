package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
import ir.maktab.homeservice.dto.OrderOfCustomerResponse;
import ir.maktab.homeservice.service.OrderOfCustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders-of-customer")
public class OrderOfCustomerController {

    private final OrderOfCustomerService orderOfCustomerService;

    //✅ ok
    @PostMapping
    public ResponseEntity<OrderOfCustomerResponse> submitOrder(
            @RequestBody @Valid
            OrderOfCustomerRequest request) {
        return ResponseEntity.ok(
                orderOfCustomerService.submitOrderForHomeService(request));
    }
}
