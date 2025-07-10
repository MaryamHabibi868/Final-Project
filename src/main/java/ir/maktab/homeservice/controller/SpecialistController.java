package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/specialist")
public class SpecialistController {

    private final SpecialistService specialistService;

    //✅
    @PostMapping("/register")
    public ResponseEntity<SpecialistResponse> registerSpecialist(
            @RequestBody @Valid
            SpecialistSaveRequest request) {
        return ResponseEntity.ok(specialistService.registerSpecialist(request));
    }

    //✅
    @PostMapping("/login")
    public ResponseEntity<SpecialistResponse> loginSpecialist(
            @RequestBody @Valid
            SpecialistLoginRequest request) {
        return ResponseEntity.ok(specialistService.loginSpecialist(request));
    }

    //✅
    @PutMapping
    public ResponseEntity<SpecialistResponse> updateSpecialist(
            @RequestBody @Valid
            SpecialistUpdateInfo request) {
        return ResponseEntity.ok(specialistService.updateSpecialistInfo(request));
    }

    //✅
    @PutMapping("/specialists/{id}/approve")
    public ResponseEntity<SpecialistSaveRequest>
    approveSpecialistRegistration(
            @PathVariable Long id) {
        return ResponseEntity.ok(specialistService.approveSpecialistRegistration(id));
    }


    //✅
    @PostMapping("/specialists/{specialistId}/home-services")
    public ResponseEntity<Void> addSpecialistToHomeService(
            @PathVariable Long specialistId,
            @RequestParam Long homeServiceId) {
        specialistService.addSpecialistToHomeService(specialistId, homeServiceId);
        return ResponseEntity.ok().build();
    }

    //✅
    @DeleteMapping("/specialists/{specialistId}/home-services")
    public ResponseEntity<Void> deleteSpecialistFromHomeService(
            @PathVariable Long specialistId,
            @RequestParam Long homeServiceId) {
        specialistService.removeSpecialistFromHomeService(specialistId, homeServiceId);
        return ResponseEntity.ok().build();
    }


}
