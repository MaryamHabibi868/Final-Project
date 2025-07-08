package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.domains.OrderOfCustomer;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.HomeServiceService;
import ir.maktab.homeservice.service.OfferOfSpecialistService;
import ir.maktab.homeservice.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/specialist")
public class SpecialistController {

    private final SpecialistService specialistService;
    private final OfferOfSpecialistService offerOfSpecialistService;
    private final HomeServiceService homeServiceService;

    @PostMapping("/register")
    public ResponseEntity<SpecialistSaveUpdateRequest> registerSpecialist(
            @RequestBody @Validated(value = ValidationGroup.Save.class)
            SpecialistSaveUpdateRequest request) {
        return ResponseEntity.ok(specialistService.registerSpecialist(request));
    }

    @PostMapping("/login")
    public ResponseEntity<SpecialistSaveUpdateRequest> loginSpecialist(
            @RequestBody @Validated(value = ValidationGroup.Update.class)
            SpecialistSaveUpdateRequest request) {
        return ResponseEntity.ok(specialistService.loginSpecialist(request));
    }

    @PutMapping("/update")
    public ResponseEntity<SpecialistSaveUpdateRequest> updateSpecialist(
            @RequestBody @Validated(value = ValidationGroup.Update.class)
            SpecialistUpdateInfo request) {
        return ResponseEntity.ok(specialistService.updateSpecialistInfo(request));
    }

    @PutMapping("/approve-specialist")
    public ResponseEntity<SpecialistSaveUpdateRequest> approveSpecialistRegistration(
            @RequestBody @Validated(value = ValidationGroup.Update.class)
            SpecialistFound request) {
        return ResponseEntity.ok(specialistService.approveSpecialistRegistration(request));
    }

    @PostMapping("/submit-offer")
    public ResponseEntity<OfferOfSpecialistRequest> submitOffer(
            @RequestBody @Valid
            OfferOfSpecialistRequest request, OrderOfCustomer order) {
        return ResponseEntity.ok(specialistService.submitOfferBySpecialist(request, order));
    }

    @PostMapping("/add-specialist-to-home-service")
    public void addSpecialistToHomeService(
            @RequestBody @Valid
            SpecialistFound specialist, HomeServiceFound homeService) {
        specialistService.addSpecialistToHomeService(specialist, homeService);
    }

    @DeleteMapping("/deleting-specialist-from-home-service")
    public void deleteSpecialistFromHomeService(
            @RequestBody @Valid
            SpecialistFound specialist, HomeServiceFound homeService) {
        specialistService.removeSpecialistFromHomeService(specialist, homeService);
    }



}
