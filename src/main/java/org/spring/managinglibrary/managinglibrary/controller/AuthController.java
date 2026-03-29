package org.spring.managinglibrary.managinglibrary.controller;

import org.spring.managinglibrary.managinglibrary.model.Member;
import org.spring.managinglibrary.managinglibrary.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // Authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Load user — must be Member since JwtService.generateToken() takes Member
        Member member = (Member) userDetailsService.loadUserByUsername(email);
        String token = jwtService.generateToken(member);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(
            org.springframework.security.core.Authentication authentication) {
        return ResponseEntity.ok(authentication.getPrincipal());
    }
    @GetMapping("/test")
    public String test() {
        return "Security is working!";
    }
}