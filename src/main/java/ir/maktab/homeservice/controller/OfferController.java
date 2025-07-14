package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.OfferSaveRequest;
import ir.maktab.homeservice.dto.OfferResponse;
import ir.maktab.homeservice.dto.OrderResponse;
import ir.maktab.homeservice.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<List<OfferResponse>> findByOffersBySpecialistId(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                offerService.findByOfferOfSpecialistId(specialistId));
    }


    @GetMapping("/sort-by-suggested-price/{orderId}")
    public ResponseEntity<List<OfferResponse>> findAllOffersBySuggestedPrice(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(
                offerService.findAllOffersBySuggestedPrice(orderId));
    }


    @GetMapping("/sort-by-specialist-score/{orderId}")
    public ResponseEntity<List<OfferResponse>> findAllOffersBySpecialistScore(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(
                offerService.findAllOffersBySpecialistScore(orderId));
    }


    @PostMapping("/pay-specialist/{offerId}")
    public ResponseEntity<String> paySpecialist(
            @PathVariable Long offerId) {
        offerService.paySpecialist(offerId);
        return ResponseEntity.ok("Transaction Successful");
    }


    @GetMapping("/find-all-orders-by-specialist-id/{specialistId}")
    public ResponseEntity<List<OrderResponse>> findAllOrdersBySpecialistId(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                offerService.findOrdersBySpecialistId(specialistId));
    }
}
