package com.selfProjectbook.SelfProjecttwoBook.controller;

import com.selfProjectbook.SelfProjecttwoBook.dto.LoginRequestDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.LoginResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.SignupResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
            ){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signupUser")
    public ResponseEntity<SignupResponseDto> signup(
            @RequestBody LoginRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto, "ROLE_USER"));
    }

    @PostMapping("/signupAdmin")
    public ResponseEntity<SignupResponseDto> signupAdmin(
            @RequestBody LoginRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto,"ROLE_ADMIN"));
    }

}
