package com.dasvoximon.railwaysystem.security;

import com.dasvoximon.railwaysystem.model.entity.sub.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    @Email private String email;
    private String password;
    @Enumerated(EnumType.STRING) private Role role;
}
