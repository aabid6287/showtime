package com.cfs.ShowTime.controller;

import com.cfs.ShowTime.dto.ApiResponse;
import com.cfs.ShowTime.dto.LoginRequest;
import com.cfs.ShowTime.dto.LoginResponse;
import com.cfs.ShowTime.dto.RegisterRequest;
import com.cfs.ShowTime.dto.UserResponse;
import com.cfs.ShowTime.entity.User;
import com.cfs.ShowTime.service.AuthService;
import com.cfs.ShowTime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByEmail(auth.getName());

        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new AccessDeniedException("Access denied: Admin role required");
        }

        List<UserResponse> users = userService.getAllUser().stream()
                .map(UserResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByEmail(auth.getName());

        if (!currentUser.getId().equals(id) && !"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new AccessDeniedException("Access denied: Cannot view another user's profile");
        }

        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }
}

