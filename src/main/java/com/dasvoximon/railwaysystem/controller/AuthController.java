package com.dasvoximon.railwaysystem.controller;

import com.dasvoximon.railwaysystem.security.AuthServices;
import com.dasvoximon.railwaysystem.security.UserRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    private final AuthServices authServices;

    @PostMapping("/register")
    public String register(@RequestBody @Valid UserRequest userRequest) {
        authServices.registerUser(userRequest);
        return userRequest.getRole() + " registered successfully";
    }

    // Test endpoints
    @GetMapping("/user/home")
    public String userHome() {
        return "Welcome User!";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Welcome Admin!";
    }
}

