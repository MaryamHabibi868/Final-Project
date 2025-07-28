package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.HomeServiceResponse;
import ir.maktab.homeservice.dto.HomeServiceSaveRequest;
import ir.maktab.homeservice.dto.HomeServiceUpdateRequest;
import ir.maktab.homeservice.service.HomeServiceService;
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
@RequestMapping("api/home-services")
public class HomeServiceController {

    private final HomeServiceService homeServiceService;


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<HomeServiceResponse> createHomeService(
            @RequestBody @Valid
            HomeServiceSaveRequest request) {
        return ResponseEntity.ok(homeServiceService.createHomeService(request));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PutMapping
    public ResponseEntity<HomeServiceResponse> updateHomeService(
            @RequestBody @Valid
            HomeServiceUpdateRequest request) {
        return ResponseEntity.ok(homeServiceService.updateHomeService(request));
    }



    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHomeService(
            @PathVariable Long id) {
        homeServiceService.deleteHomeService(id);
        return ResponseEntity.ok("Home Service Deleted");
    }


    //✅
    @GetMapping
    public ResponseEntity<Page<HomeServiceResponse>> findAllHomeServices(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(homeServiceService.findAllHomeServices(pageable));
    }


    //✅
    @GetMapping("/find-home-service-by-id/{homeServiceId}")
    public ResponseEntity<HomeServiceResponse> findHomeServiceById(
            @PathVariable Long homeServiceId) {
        return ResponseEntity.ok(homeServiceService.findHomeServiceById(homeServiceId));
    }


    //✅
    @GetMapping("/find-all-by-parent-service-id/{parentServiceId}")
    public ResponseEntity<Page<HomeServiceResponse>> findAllHomeServiceByParentServiceId(
            @PathVariable Long parentServiceId,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(homeServiceService
                .findAllHomeServiceByParentServiceId(parentServiceId, pageable));
    }
}
