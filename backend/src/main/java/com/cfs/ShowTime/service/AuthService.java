package com.cfs.ShowTime.service;

import com.cfs.ShowTime.dto.ApiResponse;
import com.cfs.ShowTime.dto.LoginRequest;
import com.cfs.ShowTime.dto.LoginResponse;
import com.cfs.ShowTime.dto.RegisterRequest;
import com.cfs.ShowTime.dto.UserResponse;
import com.cfs.ShowTime.entity.User;
import com.cfs.ShowTime.repository.UserRepository;
import com.cfs.ShowTime.security.CustomUserDetailsService;
import com.cfs.ShowTime.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public ApiResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        String role = (request.getRole() != null && !request.getRole().trim().isEmpty())
                ? request.getRole().trim().toUpperCase()
                : "USER";
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(role)
                .build();

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("User registered successfully")
                .build();
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        String storedPassword = user.getPassword();
        boolean matches = false;

        if (storedPassword != null && (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$"))) {
            matches = passwordEncoder.matches(request.getPassword(), storedPassword);
        } else {
            // Support legacy plain text passwords by auto-migrating to BCrypt upon successful login
            if (storedPassword != null && storedPassword.equals(request.getPassword())) {
                matches = true;
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);
            }
        }

        if (!matches) {
            throw new BadCredentialsException("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .success(true)
                .message("Login successful")
                .token(token)
                .user(UserResponse.fromEntity(user))
                .build();
    }
}
