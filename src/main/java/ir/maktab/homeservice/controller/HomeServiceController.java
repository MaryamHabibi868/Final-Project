package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.service.HomeServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/home-services")
public class HomeServiceController {

    private final HomeServiceService homeServiceService;

    // ☑️ final check
    //✅ ok
    @PostMapping
    public ResponseEntity<HomeServiceResponse> createHomeService(
            @RequestBody @Valid
            HomeServiceSaveRequest request) {
        return ResponseEntity.ok(homeServiceService.createHomeService(request));
    }

    // ☑️ final check
    //✅
    @PutMapping
    public ResponseEntity<HomeServiceResponse> updateHomeService(
            @RequestBody @Valid
            HomeServiceUpdateRequest request) {
        return ResponseEntity.ok(homeServiceService.updateHomeService(request));
    }

    // ☑️ final check
    //✅
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHomeService(
            @PathVariable Long id) {
        homeServiceService.deleteHomeService(id);
        return ResponseEntity.ok("Home Service Deleted");
    }

    // ☑️ final check
    //✅
    @GetMapping
    public ResponseEntity<List<HomeServiceResponse>> findAllHomeServices() {
        return ResponseEntity.ok(homeServiceService.findAllHomeServices());
    }

    // ☑️ final check
    @GetMapping("/find-home-service-by-id/{homeServiceId}")
    public ResponseEntity<HomeServiceResponse> findHomeServiceById(
            @PathVariable Long homeServiceId) {
        return ResponseEntity.ok(homeServiceService.findHomeServiceById(homeServiceId));
    }

    // ☑️ final check
    @GetMapping("/find-all-by-parent-service-id/{parentServiceId}")
    public ResponseEntity<List<HomeServiceResponse>> findAllHomeServiceByParentServiceId(
            @PathVariable Long parentServiceId) {
        return ResponseEntity.ok(homeServiceService
                .findAllHomeServiceByParentServiceId(parentServiceId));
    }
}
