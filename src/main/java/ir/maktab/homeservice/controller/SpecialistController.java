package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/specialist")
public class SpecialistController {

    private final SpecialistService specialistService;


    @PostMapping("/register")
    public ResponseEntity<SpecialistResponse> registerSpecialist(
            @RequestBody @Valid
            SpecialistSaveRequest request) {
        return ResponseEntity.ok(specialistService.registerSpecialist(request));
    }


    @PostMapping("/login")
    public ResponseEntity<SpecialistResponse> loginSpecialist(
            @RequestBody @Valid
            SpecialistLoginRequest request) {
        return ResponseEntity.ok(specialistService.loginSpecialist(request));
    }


    @PutMapping
    public ResponseEntity<SpecialistResponse> updateSpecialist(
            @RequestBody @Valid
            SpecialistUpdateInfo request) {
        return ResponseEntity.ok(specialistService.updateSpecialistInfo(request));
    }


    @PutMapping("/specialists/{specialistId}/approve")
    public ResponseEntity<SpecialistResponse>
    approveSpecialistRegistration(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                specialistService.approveSpecialistRegistration(specialistId));
    }



    @PostMapping("/add-specialists/{specialistId}/home-services")
    public ResponseEntity<String> addSpecialistToHomeService(
            @PathVariable Long specialistId,
            @RequestParam Long homeServiceId) {
        specialistService.addSpecialistToHomeService(specialistId, homeServiceId);
        return ResponseEntity.ok("Specialist added to home service");
    }


    @DeleteMapping("/specialists/{specialistId}/home-services")
    public ResponseEntity<String> deleteSpecialistFromHomeService(
            @PathVariable Long specialistId,
            @RequestParam Long homeServiceId) {
        specialistService.removeSpecialistFromHomeService(specialistId, homeServiceId);
        return ResponseEntity.ok("Specialist removed from home service");
    }


    @GetMapping
    public ResponseEntity<List<SpecialistResponse>> findAllSpecialists() {
        return ResponseEntity.ok(specialistService.findAllSpecialists());
    }


    @GetMapping("/filter-by-first-name")
    public ResponseEntity<List<SpecialistResponse>> findAllByFirstNameContainsIgnoreCase(
            @RequestParam String firstName) {
        return ResponseEntity.ok(
                specialistService
                        .findAllByFirstNameContainsIgnoreCaseOrderByIdAsc(firstName));
    }


    @GetMapping("/filter-by-last-name")
    public ResponseEntity<List<SpecialistResponse>> findAllByLastNameContainsIgnoreCase(
            @RequestParam String lastName) {
        return ResponseEntity.ok(
                specialistService
                        .findAllByLastNameContainsIgnoreCaseOrderByIdAsc(lastName));
    }


    @GetMapping("/find-home-services-by-title")
    public ResponseEntity<List<SpecialistResponse>> findAllHomeServicesByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(
                specialistService.findAllByHomeServiceTitle(title));
    }


    @GetMapping("/find-all-home-services-by-specialist-id/{specialistId}")
    public ResponseEntity<List<HomeServiceResponse>> findAllHomeServicesBySpecialistId(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                specialistService.findAllHomeServicesBySpecialistId(specialistId));
    }


    @GetMapping("/score-between/{lower}/{higher}")
    public ResponseEntity<List<SpecialistResponse>> findAllByScoreBetween(
            @PathVariable Double lower,
            @PathVariable Double higher) {
        return ResponseEntity.ok(
                specialistService.findAllByScoreIsBetween(lower, higher));
    }


    @GetMapping("/get-score-by-specialist-id/{specialistId}")
    public ResponseEntity<Double> findScoreBySpecialistId(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                specialistService.findScoreBySpecialistId(specialistId));
    }


    @GetMapping("/find-all-transaction-by-specialist-id/{specialistId}")
    public ResponseEntity<List<TransactionResponse>> findAllTransactionsBySpecialistId(
            @PathVariable Long specialistId) {
        return ResponseEntity.ok(
                specialistService.findAllTransactionsBySpecialistId(specialistId));
    }

    @PostMapping("/in-activate-specialists")
    public ResponseEntity<String> inActivateSpecialists() {
        specialistService.inActivateSpecialist();
        return ResponseEntity.ok(
                "Specialist by score less than ZERO In Activated");
    }

}
