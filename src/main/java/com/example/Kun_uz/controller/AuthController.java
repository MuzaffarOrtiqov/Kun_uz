package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.history.SmsHistoryDTO;
import com.example.Kun_uz.dto.auth.AuthDTO;
import com.example.Kun_uz.dto.auth.JwtDTO;
import com.example.Kun_uz.dto.auth.RegistrationDTO;
import com.example.Kun_uz.dto.auth.SmsLoginDTO;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.example.Kun_uz.service.AuthService;
import com.example.Kun_uz.util.SecurityUtil;
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
    public ResponseEntity<String> registration( @RequestBody RegistrationDTO dto) {
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
    @GetMapping("/verificationWithEmail/{token}")
    public ResponseEntity<String> verification(@RequestHeader("Authorization") String token) {
       JwtDTO dto =  SecurityUtil.getJwtDTO(token);
        String body = authService.authorizationVerification(dto.getId());
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
    public ResponseEntity<ProfileDTO> loginWithEmail (@Valid @RequestBody AuthDTO dto) {
        return ResponseEntity.ok().body(authService.loginWithEmail(dto));
    }
    @PostMapping("/loginWithPhone")
    public ResponseEntity<String> loginWithPhone (@Valid @RequestBody SmsLoginDTO dto) {
        return ResponseEntity.ok().body(authService.loginWithPhone(dto));
    }





}
