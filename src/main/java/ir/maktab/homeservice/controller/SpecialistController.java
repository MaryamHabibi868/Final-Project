package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
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

    @PostMapping("/register")
    public ResponseEntity<SpecialistSaveUpdateRequest> registerSpecialist(
            @RequestBody @Validated(value = ValidationGroup.Save.class)
            SpecialistSaveUpdateRequest request) {
        return ResponseEntity.ok(specialistService.registerSpecialist(request));
    }

    @PostMapping("/login")
    public ResponseEntity<SpecialistSaveUpdateRequest> loginSpecialist(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            SpecialistSaveUpdateRequest request) {
        return ResponseEntity.ok(specialistService.loginSpecialist(request));
    }

    @PutMapping("/update")
    public ResponseEntity<SpecialistSaveUpdateRequest> updateSpecialist(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            SpecialistUpdateInfo request) {
        return ResponseEntity.ok(specialistService.updateSpecialistInfo(request));
    }

    @PutMapping("/approve-specialist")
    public ResponseEntity<SpecialistSaveUpdateRequest> approveSpecialistRegistration(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            SpecialistFound request) {
        return ResponseEntity.ok(specialistService.approveSpecialistRegistration(request));
    }

    @PostMapping("/submit-offer")
    public ResponseEntity<OfferOfSpecialistRequest> submitOffer(
            @RequestBody @Valid
            OfferOfSpecialistRequest request) {
        return ResponseEntity.ok(offerOfSpecialistService.submitOffer(request));
    }


}
