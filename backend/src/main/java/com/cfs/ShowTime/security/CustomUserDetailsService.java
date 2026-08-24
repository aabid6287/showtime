package com.cfs.ShowTime.security;

import com.cfs.ShowTime.entity.User;
import com.cfs.ShowTime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String roleStr = user.getRole() != null ? user.getRole().toUpperCase() : "USER";
        if (roleStr.startsWith("ROLE_")) {
            roleStr = roleStr.substring(5);
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleStr));
        authorities.add(new SimpleGrantedAuthority(roleStr));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
