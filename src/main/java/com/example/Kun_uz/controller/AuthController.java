package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.auth.LoginDTO;
import com.example.Kun_uz.dto.auth.RegistrationDTO;
import com.example.Kun_uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    // Registration process
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registration(dto);
        return ResponseEntity.ok().body(body);
    }
    //Verfication of a user
    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verification(@PathVariable("userId") Integer userId) {
        String body = authService.authorizationVerification(userId);
        return ResponseEntity.ok().body(body);
    }
    // Log in process if a user has created an account earlier
    @PostMapping("/login")
    public ResponseEntity<String> login (@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }
    // Resending an email if a user hasn't received one.
    @PostMapping("/resend")
    public ResponseEntity<String> resendEmail (@Valid @RequestParam(name = "email") String email) {
        return ResponseEntity.ok().body(authService.authorizeResend(email));
    }





}
