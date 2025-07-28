package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseEntity.ok(
                specialistService.registerSpecialist(request));
    }


    @PostMapping("/login")
    public ResponseEntity<SpecialistResponse> loginSpecialist(
            @RequestBody @Valid
            SpecialistLoginRequest request) {
        return ResponseEntity.ok(
                specialistService.loginSpecialist(request));
    }


    //✅
    @PreAuthorize("hasAnyAuthority('ROLE_SPECIALIST' , 'ROLE_MANAGER' )")
    @PutMapping
    public ResponseEntity<SpecialistResponse> updateSpecialist(
            @RequestBody @Valid
            SpecialistUpdateInfo request) {
        return ResponseEntity.ok(
                specialistService.updateSpecialistInfo(request));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PutMapping("/specialists/{specialistId}/approve")
    public ResponseEntity<SpecialistResponse>
    approveSpecialistRegistration(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                specialistService.approveSpecialistRegistration(specialistId));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/add-specialists/{specialistId}/home-services")
    public ResponseEntity<AddRemoveSToHResponse> addSpecialistToHomeService(
            @PathVariable Long specialistId,
            @RequestParam Long homeServiceId) {
        return ResponseEntity.ok(
                specialistService.addSpecialistToHomeService
                        (specialistId, homeServiceId));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @DeleteMapping("/specialists/{specialistId}/home-services")
    public ResponseEntity<AddRemoveSToHResponse> deleteSpecialistFromHomeService(
            @PathVariable Long specialistId,
            @RequestParam Long homeServiceId) {
        return ResponseEntity.ok(
                specialistService.removeSpecialistFromHomeService(
                        specialistId, homeServiceId));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/find-all-by-home-service-id/{homeServiceId}")
    public ResponseEntity<Page<SpecialistResponse>> findAllByHomeServiceId(
            @PathVariable Long homeServiceId,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                specialistService
                        .findAllByHomeServiceId(homeServiceId, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/find-all-home-services-by-specialist-id/{specialistId}")
    public ResponseEntity<Page<HomeServiceResponse>>
    findAllHomeServicesBySpecialistId(
            @PathVariable Long specialistId,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                specialistService
                        .findAllHomeServicesBySpecialistId(specialistId, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/score-between/{lower}/{higher}")
    public ResponseEntity<Page<SpecialistResponse>> findAllByScoreBetween(
            @PathVariable Double lower,
            @PathVariable Double higher,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                specialistService.findAllByScoreIsBetween(lower, higher, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_SPECIALIST')")
    @GetMapping("/get-score-by-specialist-id")
    public ResponseEntity<ScoreResponse> findScoreBySpecialistId() {
        return ResponseEntity.ok(
                specialistService.findScoreBySpecialistId());
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_SPECIALIST')")
    @GetMapping("/find-all-transaction-by-specialist-id")
    public ResponseEntity<Page<TransactionResponse>>
    findAllTransactionsBySpecialistId(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                specialistService.findAllTransactionsBySpecialistId(pageable));
    }


    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/in-activate-specialists")
    public ResponseEntity<String> inActivateSpecialists() {
        specialistService.inActivateSpecialist();
        return ResponseEntity.ok(
                "Specialist by score less than ZERO In Activated");
    }


    //✅
    @GetMapping("/verify")
    public ResponseEntity<VerifiedUserResponse> verifySpecialistEmail(
            @RequestParam("token") String token) {
        return ResponseEntity.ok(
                specialistService.verifySpecialistEmail(token));
    }

}
