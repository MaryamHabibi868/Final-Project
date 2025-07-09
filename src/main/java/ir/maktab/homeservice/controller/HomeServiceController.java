package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.service.HomeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/home-services")
public class HomeServiceController {

    private final HomeServiceService homeServiceService;

    //✅ ok
    @PostMapping
    public ResponseEntity<HomeServiceResponse> createHomeService(
            @RequestBody
            HomeServiceSaveRequest request) {
        return ResponseEntity.ok(homeServiceService.createHomeService(request));
    }

    //✅
    @PutMapping
    public ResponseEntity<HomeServiceResponse> updateHomeService(
            @RequestBody
            HomeServiceUpdateRequest request) {
        return ResponseEntity.ok(homeServiceService.updateHomeService(request));
    }

    //✅
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomeService(
            @PathVariable Long id) {
        homeServiceService.deleteHomeService(id);
        return ResponseEntity.noContent().build();
    }

    //✅
    @GetMapping
    public ResponseEntity<List<HomeServiceResponse>> findAllHomeServices() {
        return ResponseEntity.ok(homeServiceService.findAllHomeServices());
    }
}
