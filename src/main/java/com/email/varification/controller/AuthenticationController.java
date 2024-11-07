package com.email.varification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.varification.dto.LoginUserDto;
import com.email.varification.dto.RegisterUserDto;
import com.email.varification.dto.VerifyUserDto;
import com.email.varification.model.User;
import com.email.varification.response.LoginResponse;
import com.email.varification.service.AuthenticationService;
import com.email.varification.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public ResponseEntity<User> registerAccoutn(@RequestBody RegisterUserDto register) {
        User registerUser = authenticationService.signup(register);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponse> loginAccount(@RequestBody LoginUserDto request) {
        User authenticatedUser = authenticationService.login(request);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse response = new LoginResponse(jwtToken, jwtService.getExpireationTime());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerification(@RequestBody String email) {
        try {
            authenticationService.resentVerificationCode(email);
            return ResponseEntity.ok("Verify code sent!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
