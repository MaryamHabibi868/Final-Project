package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.CustomerService;
import ir.maktab.homeservice.service.HomeServiceService;
import ir.maktab.homeservice.service.OfferOfSpecialistService;
import ir.maktab.homeservice.service.OrderOfCustomerService;
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


    //✅
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody @Valid
            CustomerSaveRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }

    //✅
    @PostMapping("/login")
    public ResponseEntity<CustomerResponse> loginCustomer(
            @RequestBody @Valid
            CustomerLoginRequest request) {
        return ResponseEntity.ok(customerService.loginCustomer(request));
    }

    //✅
    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(
            @RequestBody @Valid
            CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }



    @PostMapping("/submit-feedback")
    public ResponseEntity<FeedbackRequest> submitFeedback(
            @RequestBody @Valid
            FeedbackRequest feedbackRequest) {
        return ResponseEntity.ok(feedbackRequest);
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
