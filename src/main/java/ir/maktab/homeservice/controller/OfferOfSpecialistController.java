package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.OfferOfSpecialistRequest;
import ir.maktab.homeservice.dto.OfferOfSpecialistResponse;
import ir.maktab.homeservice.service.OfferOfSpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/offers-of-specialist")
public class OfferOfSpecialistController {

    private final OfferOfSpecialistService offerOfSpecialistService;

    //✅ ok
    @PostMapping
    public ResponseEntity<OfferOfSpecialistResponse> submitOfferToOrder(
            @RequestBody @Valid
            OfferOfSpecialistRequest request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.submitOfferToOrder(request));
    }

    //✅ ok
    @PostMapping("/choose-offer")
    public ResponseEntity<OfferOfSpecialistResponse> chooseOffer(
            @RequestBody @Valid
            OfferOfSpecialistResponse request) {
        return ResponseEntity.ok
                (offerOfSpecialistService.chooseOfferOfSpecialist(request));
    }

    //✅ ok
    @PostMapping("/start-service")
    public ResponseEntity<OfferOfSpecialistResponse> startService(
            @RequestBody @Valid
            OfferOfSpecialistResponse request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.startService(request));
    }

    //✅ ok
    @PostMapping("/end-service")
    public ResponseEntity<OfferOfSpecialistResponse> endService(
            @RequestBody @Valid
            OfferOfSpecialistResponse request) {
        return ResponseEntity.ok(
                offerOfSpecialistService.endService(request));
    }

    //✅ ok
    @GetMapping("/specialist-id/{specialistId}")
    public ResponseEntity<List<OfferOfSpecialistResponse>> findByOffersBySpecialistId(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                offerOfSpecialistService.findByOfferOfSpecialistId(specialistId));
    }

    //✅ ok
    @GetMapping("/customer-id/{customerId}")
    public ResponseEntity<List<OfferOfSpecialistResponse>>
    findAllOfferOfSpecialistByCustomerId(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(
                offerOfSpecialistService.
                        findAllOfferOfSpecialistOrderByCustomerId(customerId));
    }
}
