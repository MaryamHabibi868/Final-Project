package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.dto.PaymentResponse;
import ir.maktab.homeservice.service.OfferService;
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
@RequestMapping("api/offers")
public class OfferController {

    private final OfferService offerService;


    //✅
    @PreAuthorize("hasAuthority('ROLE_SPECIALIST')")
    @PostMapping
    public ResponseEntity<OfferResponse> submitOfferToOrder(
            @RequestBody @Valid
            OfferSaveRequest request) {
        return ResponseEntity.ok(
                offerService.submitOfferToOrder(request));
    }

    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/choose-offer/{offerId}")
    public ResponseEntity<OfferResponse> chooseOffer(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(
                offerService.chooseOfferOfSpecialist(offerId));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/start-service/{offerId}")
    public ResponseEntity<OfferResponse> startService(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(
                offerService.startService(offerId));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/end-service/{offerId}")
    public ResponseEntity<OfferResponse> endService(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(
                offerService.endService(offerId));
    }


    @GetMapping("/specialist-id")
    public ResponseEntity<Page<OfferResponse>> findByOffersBySpecialistId(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findByOfferOfSpecialistId(pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/sort-by-suggested-price/{orderId}")
    public ResponseEntity<Page<OfferResponse>> findAllOffersBySuggestedPrice(
            @PathVariable Long orderId,
     @PageableDefault(size = 10, page = 0, sort = "suggestedPrice") Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findAllOffersBySuggestedPrice(orderId, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/sort-by-specialist-score/{orderId}")
    public ResponseEntity<Page<OfferResponse>> findAllOffersBySpecialistScore(
            @PathVariable Long orderId,
            @PageableDefault(size = 10, page = 0, sort = "specialistScore",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findAllOffersBySpecialistScore(orderId, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/pay-specialist/{offerId}")
    public ResponseEntity<PaymentResponse> paySpecialist(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(
                offerService.paySpecialist(offerId));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_SPECIALIST')")
    @GetMapping("/find-all-orders-by-specialist-id")
    public ResponseEntity<Page<OrderResponse>> findAllOrdersBySpecialistId(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findOrdersBySpecialistId(pageable));
    }
}
