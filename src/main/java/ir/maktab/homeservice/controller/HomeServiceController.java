package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.HomeServiceFound;
import ir.maktab.homeservice.dto.HomeServiceSaveUpdateRequest;
import ir.maktab.homeservice.dto.ValidationGroup;
import ir.maktab.homeservice.service.HomeServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/home-service")
public class HomeServiceController {

    private final HomeServiceService homeServiceService;

    @PostMapping
    public ResponseEntity<HomeServiceSaveUpdateRequest> createHomeService(
            @RequestBody @Validated(value = ValidationGroup.Save.class)
            HomeServiceSaveUpdateRequest request) {
        return ResponseEntity.ok(homeServiceService.createHomeService(request));
    }

    @PutMapping
    public ResponseEntity<HomeServiceSaveUpdateRequest> updateHomeService(
            @RequestBody @Validated (value = ValidationGroup.Update.class)
            HomeServiceSaveUpdateRequest request) {
        return ResponseEntity.ok(homeServiceService.updateHomeService(request));
    }

    @DeleteMapping
    public void deleteHomeService(
            @RequestBody @Valid HomeServiceFound request) {
        homeServiceService.deleteHomeService(request);
    }
}
