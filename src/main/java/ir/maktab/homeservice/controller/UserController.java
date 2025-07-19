package ir.maktab.homeservice.controller;


import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/filter-by-role")
    public ResponseEntity<List<UserResponse>> filterByRole(
            @RequestParam String role) {
        return ResponseEntity.ok(userService.filterByRole(role));
    }
}
