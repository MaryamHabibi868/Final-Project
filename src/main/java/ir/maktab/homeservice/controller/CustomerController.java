package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.CustomerSaveUpdateRequest;
import ir.maktab.homeservice.dto.ValidationGroup;
import ir.maktab.homeservice.service.CustomerService;
import ir.maktab.homeservice.service.HomeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final HomeServiceService homeServiceService;

    @PostMapping("/register")
    public ResponseEntity<CustomerSaveUpdateRequest> registerCustomer(
            @RequestBody @Validated(value = ValidationGroup.Save.class)
            CustomerSaveUpdateRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerSaveUpdateRequest> loginCustomer(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            CustomerSaveUpdateRequest request) {
        return ResponseEntity.ok(customerService.loginCustomer(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerSaveUpdateRequest> updateCustomer(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            CustomerSaveUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }

    @GetMapping("/findAllHomeServices")
    public List<HomeService> findAllHomeServices() {
        return homeServiceService.findAll();
    }
}
