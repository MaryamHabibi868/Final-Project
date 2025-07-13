package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/managers")
public class ManagerController {

    private final ManagerService managerService;


    //✅
    @PostMapping("/register")
    public ResponseEntity<ManagerResponse> registerManager(
            @RequestBody @Valid
            ManagerSaveRequest request) {
        return ResponseEntity.ok(managerService.registerManager(request));
    }

    //✅
    @PostMapping("/login")
    public ResponseEntity<ManagerResponse> loginManager(
            @RequestBody @Valid
            ManagerLoginRequest request) {
        return ResponseEntity.ok(managerService.loginManager(request));
    }

    //✅
    @PutMapping
    public ResponseEntity<ManagerResponse> updateManager(
            @RequestBody @Valid
            ManagerUpdateRequest request) {
        return ResponseEntity.ok(managerService.updateManager(request));
    }
}
