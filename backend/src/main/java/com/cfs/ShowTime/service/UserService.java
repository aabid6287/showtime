package com.cfs.ShowTime.service;

import com.cfs.ShowTime.dto.LoginRequest;
import com.cfs.ShowTime.dto.UserRequest;
import com.cfs.ShowTime.entity.User;
import com.cfs.ShowTime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //register user
    public User register(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already exists: "+request.getEmail());
        }

        User user=User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role("USER")
                .build();
        return userRepository.save(user);
    }

    //login user
    public User login(LoginRequest request) {
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new BadCredentialsException("Invalid email or password"));
        String stored = user.getPassword();
        boolean match = false;
        if (stored != null && (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$"))) {
            match = passwordEncoder.matches(request.getPassword(), stored);
        } else if (stored != null && stored.equals(request.getPassword())) {
            match = true;
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        }

        if(!match)
        {
            throw new BadCredentialsException("Invalid email or password");
        }
        return user;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id: "+id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found with email: "+email));
    }
}

