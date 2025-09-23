package com.dasvoximon.railwaysystem.security;

import com.dasvoximon.railwaysystem.model.entity.Users;
import com.dasvoximon.railwaysystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("Users not found");
        }
        return User.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .roles(users.getRole().name())
                .build();
    }
}
