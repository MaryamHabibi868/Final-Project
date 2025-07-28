package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;


    //✅
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody @Valid
            CustomerSaveRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }


    @PostMapping("/login")
    public ResponseEntity<CustomerResponse> loginCustomer(
            @RequestBody @Valid
            CustomerLoginRequest request) {
        return ResponseEntity.ok(customerService.loginCustomer(request));
    }


    //✅
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER','ROLE_MANAGER')")
    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(
            @RequestBody @Valid
            CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }


    //✅
    @GetMapping("/verify")
    public ResponseEntity<VerifiedUserResponse> verifyCustomerEmail(
            @RequestParam("token") String token) {
        return ResponseEntity.ok(customerService.verifyCustomerEmail(token));
    }
}
