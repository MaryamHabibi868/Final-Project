package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.UserFilterRequest;
import ir.maktab.homeservice.dto.UserResponse;
import ir.maktab.homeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/filter-by-role")
    public ResponseEntity<Page<UserResponse>> filterByRole(
            @RequestParam String role,
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userService.filterByRole(role, pageable));
    }


    //✅
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping ("/filter-users")
    public ResponseEntity<Page<UserResponse>> filterUsers(
            @RequestBody UserFilterRequest request,
            @PageableDefault(size = 10, sort = "id",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserResponse> users = userService.filterUsers(request, pageable);
        return ResponseEntity.ok(users);
    }
}
