package ir.maktab.homeservice.controller;


import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<UserResponse>> filterByRole(
            @RequestParam String role,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userService.filterByRole(role, pageable));
    }

    @GetMapping ("/filter-by-name")
    public ResponseEntity<Page<UserResponse>> filterByName(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(
                userService.findAllByNameFilter(firstName, lastName, pageable));
    }
}
