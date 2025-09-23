package com.dasvoximon.railwaysystem.security;

import com.dasvoximon.railwaysystem.model.entity.Users;
import com.dasvoximon.railwaysystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRequest userRequest) {
        Users user = new Users();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());

        userRepository.save(user);
    }
}
