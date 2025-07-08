package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import ir.maktab.homeservice.dto.ValidationGroup;
import ir.maktab.homeservice.service.HomeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/home-services")
public class HomeServiceController {

    private final HomeServiceService homeServiceService;

    //✅ ok
    @PostMapping
    public ResponseEntity<HomeServiceSaveUpdateRequest> createHomeService(
            @RequestBody @Validated(value = ValidationGroup.Save.class)
            HomeServiceSaveUpdateRequest request) {
        return ResponseEntity.ok(homeServiceService.createHomeService(request));
    }

    //✅
    @PutMapping
    public ResponseEntity<HomeServiceSaveUpdateRequest> updateHomeService(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            HomeServiceSaveUpdateRequest request) {
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
    public ResponseEntity<List<HomeServiceSaveUpdateRequest>> findAllHomeServices() {
        return ResponseEntity.ok(homeServiceService.findAllHomeServices());
    }
}
