package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;


    // ☑️ final check
    //✅
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody @Valid
            CustomerSaveRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }

    // ☑️ final check
    //✅
    @PostMapping("/login")
    public ResponseEntity<CustomerResponse> loginCustomer(
            @RequestBody @Valid
            CustomerLoginRequest request) {
        return ResponseEntity.ok(customerService.loginCustomer(request));
    }

    // ☑️ final check
    //✅
    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(
            @RequestBody @Valid
            CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }

    // ☑️ final check
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    // ☑️ final check
    //✅
    @GetMapping("/filter-by-first-name")
    public ResponseEntity<List<CustomerResponse>> findAllByFirstNameContainsIgnoreCase(
            @RequestParam String firstName) {
        return ResponseEntity.ok(
                customerService
                        .findAllByFirstNameContainsIgnoreCaseOrderByIdAsc(firstName));
    }

    // ☑️ final check
    //✅
    @GetMapping("/filter-by-last-name")
    public ResponseEntity<List<CustomerResponse>> findAllByLastNameContainsIgnoreCase(
            @RequestParam String lastName) {
        return ResponseEntity.ok(
                customerService
                        .findAllByLastNameContainsIgnoreCaseOrderByIdAsc(lastName));
    }





  /*  @GetMapping("/find-all-offers-to-order")
    public ResponseEntity<List<OfferOfSpecialistRequest>> findAllOffersToOrder(
            @RequestBody
            CustomerUpdateRequest request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.
                        findAllOffersOfSpecialistsByCustomerId(request));
    }*/


}
