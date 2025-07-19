package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.OfferService;
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
@RequestMapping("api/offers")
public class OfferController {

    private final OfferService offerService;


    @PostMapping
    public ResponseEntity<OfferResponse> submitOfferToOrder(
            @RequestBody @Valid
            OfferSaveRequest request) {

        return ResponseEntity.ok(offerService.submitOfferToOrder(request));
    }


    @PostMapping("/choose-offer/{offerId}")
    public ResponseEntity<OfferResponse> chooseOffer(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(offerService.chooseOfferOfSpecialist(offerId));
    }


    @PostMapping("/start-service/{offerId}")
    public ResponseEntity<OfferResponse> startService(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(
                offerService.startService(offerId));
    }


    @PostMapping("/end-service/{offerId}")
    public ResponseEntity<OfferResponse> endService(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(
                offerService.endService(offerId));
    }


    @GetMapping("/specialist-id/{specialistId}")
    public ResponseEntity<Page<OfferResponse>> findByOffersBySpecialistId(
            @PathVariable Long specialistId,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findByOfferOfSpecialistId(specialistId, pageable));
    }


    @GetMapping("/sort-by-suggested-price/{orderId}")
    public ResponseEntity<Page<OfferResponse>> findAllOffersBySuggestedPrice(
            @PathVariable Long orderId,
     @PageableDefault(size = 10, page = 0, sort = "suggestedPrice") Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findAllOffersBySuggestedPrice(orderId, pageable));
    }


    @GetMapping("/sort-by-specialist-score/{orderId}")
    public ResponseEntity<Page<OfferResponse>> findAllOffersBySpecialistScore(
            @PathVariable Long orderId,
            @PageableDefault(size = 10, page = 0, sort = "specialistScore",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findAllOffersBySpecialistScore(orderId, pageable));
    }


    @PostMapping("/pay-specialist/{offerId}")
    public ResponseEntity<String> paySpecialist(
            @PathVariable Long offerId) {
        offerService.paySpecialist(offerId);
        return ResponseEntity.ok("Transaction Successful");
    }


    @GetMapping("/find-all-orders-by-specialist-id/{specialistId}")
    public ResponseEntity<Page<OrderResponse>> findAllOrdersBySpecialistId(
            @PathVariable Long specialistId,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                offerService.findOrdersBySpecialistId(specialistId, pageable));
    }
}
