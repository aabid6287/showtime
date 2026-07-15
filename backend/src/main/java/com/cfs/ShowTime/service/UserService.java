package com.cfs.ShowTime.service;

import com.cfs.ShowTime.dto.LoginRequest;
import com.cfs.ShowTime.dto.UserRequest;
import com.cfs.ShowTime.entity.User;
import com.cfs.ShowTime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //register user
    public User register(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already exists: "+request.getEmail());
        }

        User user=User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .build();
        return userRepository.save(user);
    }

    //login user
    public User login(LoginRequest request) {
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("User not found with email: "+request.getEmail()));
        if(!user.getPassword().equals(request.getPassword()))
        {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with email: "+id));
    }
}
