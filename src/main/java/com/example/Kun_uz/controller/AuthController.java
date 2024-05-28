package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.SmsHistoryDTO;
import com.example.Kun_uz.dto.auth.EmailLoginDTO;
import com.example.Kun_uz.dto.auth.RegistrationDTO;
import com.example.Kun_uz.dto.auth.SmsLoginDTO;
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
    // Registration with email
    @PostMapping("/registrationWithEmail")
    public ResponseEntity<String> registrationWithEmail(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registrationWithEmail(dto);
        return ResponseEntity.ok().body(body);
    }

    // Registration with sms
    @PostMapping("/registrationWithPhone")
    public ResponseEntity<String> registrationWithSms(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registrationWithPhone(dto);
        return ResponseEntity.ok().body(body);
    }

    //Verfication of a user
    @GetMapping("/verificationWithEmail/{userId}")
    public ResponseEntity<String> verification(@PathVariable("userId") Integer userId) {
        String body = authService.authorizationVerification(userId);
        return ResponseEntity.ok().body(body);
    }
    // Verification with Sms
    @GetMapping("/verificationWithSms")
    public ResponseEntity<String> verificationWithSms(@RequestBody SmsHistoryDTO dto ) {
        String body = authService.authorizationVerificationWithCode(dto);
        return ResponseEntity.ok().body(body);
    }

    // Resending an email if a user hasn't received one.
    @PostMapping("/resendEmail")
    public ResponseEntity<String> resendEmail (@Valid @RequestParam(name = "email") String email) {
        return ResponseEntity.ok().body(authService.authorizeResend(email));
    }
    // Resending a code if a user hasn't received one
    @PostMapping("/resendCode")
    public ResponseEntity<String> resendCode (@Valid @RequestParam(name = "phone") String phone) {
        return ResponseEntity.ok().body(authService.resendCode(phone));
    }
    // Log in process if a user has created an account earlier
    @PostMapping("/loginWithEmail")
    public ResponseEntity<String> loginWithEmail (@Valid @RequestBody EmailLoginDTO dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }
    @PostMapping("/loginWithPhone")
    public ResponseEntity<String> loginWithPhone (@Valid @RequestBody SmsLoginDTO dto) {
        return ResponseEntity.ok().body(authService.loginWithPhone(dto));
    }





}
