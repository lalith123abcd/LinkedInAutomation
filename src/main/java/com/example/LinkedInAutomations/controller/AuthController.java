package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.config.security.JwtUtil;
import com.example.LinkedInAutomations.dto.LoginUserDTO;
import com.example.LinkedInAutomations.dto.RegisterUserDTO;
import com.example.LinkedInAutomations.entity.User;

import com.example.LinkedInAutomations.service.RegisterUserService;
import com.example.LinkedInAutomations.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RegisterUserService registerUserService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = registerUserService.registerUser(
                registerUserDTO.getEmail(),
                registerUserDTO.getPassword()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );

        UserDetails userDetails =
                userService.loadUserByUsername(loginUserDTO.getEmail());



        String token = jwtUtil.generateToken(
                userDetails.getUsername()

        );

        return ResponseEntity.ok(token);
    }
}