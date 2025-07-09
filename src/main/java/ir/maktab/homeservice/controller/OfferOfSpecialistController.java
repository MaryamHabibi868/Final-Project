package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistResponse;
import ir.maktab.homeservice.dto.OrderOfCustomerRequest;
import ir.maktab.homeservice.service.OfferOfSpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/offers-of-specialist")
public class OfferOfSpecialistController {
    private final OfferOfSpecialistService offerOfSpecialistService;

    //✅ ok
    @PostMapping("/order-of-customers/{orderOfCustomerId}/offers")
    public ResponseEntity<OfferOfSpecialistResponse> submitOfferToOrder(
            @PathVariable Long orderOfCustomerId,
            @RequestBody @Valid
            OfferOfSpecialistRequest request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.submitOfferToOrder(request ,orderOfCustomerId));
    }
}
