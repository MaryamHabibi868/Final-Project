package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.domains.HomeService;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.CustomerService;
import ir.maktab.homeservice.service.HomeServiceService;
import ir.maktab.homeservice.service.OfferOfSpecialistService;
import ir.maktab.homeservice.service.OrderOfCustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ParameterScriptAssert;
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
    private final OrderOfCustomerService orderOfCustomerService;
    private final OfferOfSpecialistService offerOfSpecialistService;

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

    @PostMapping("/submitOrder")
    public ResponseEntity<OrderOfCustomerRequest> submitOrder(
            @RequestBody @Valid
            OrderOfCustomerRequest request) {
        return ResponseEntity.ok(orderOfCustomerService.submitOrder(request));
    }

    @PostMapping("/submit-feedback")
    public ResponseEntity<FeedbackSubmit> submitFeedback(
            @RequestBody @Valid
            FeedbackSubmit feedbackSubmit) {
        return ResponseEntity.ok(feedbackSubmit);
    }

    @GetMapping("/find-all-offers-to-order")
    public ResponseEntity<List<OfferOfSpecialistRequest>> findAllOffersToOrder(
            @RequestBody
            CustomerSaveUpdateRequest request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.
                        findAllOffersOfSpecialistsByCustomerId(request));
    }

    @PostMapping("/choose-offer")
    public ResponseEntity<OfferOfSpecialistRequest> chooseOffer(
            @RequestBody @Valid
            OfferOfSpecialistRequest request) {
        return ResponseEntity.ok
                (offerOfSpecialistService.chooseOfferOfSpecialist(request));
    }

    @PostMapping("/start-service")
    public ResponseEntity<OfferOfSpecialistRequest> startService(
            @RequestBody @Valid
            OfferOfSpecialistRequest request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.startService(request));
    }

    @PostMapping("/end-service")
    public ResponseEntity<OfferOfSpecialistRequest> endService(
            @RequestBody @Valid
            OfferOfSpecialistRequest request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.endService(request));
    }
}
