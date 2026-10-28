package com.example.PMS.security.auth.controller;

import com.example.PMS.common.response.BaseResponse;
import com.example.PMS.security.auth.dto.request.LoginRequest;
import com.example.PMS.security.auth.dto.request.RegisterRequest;
import com.example.PMS.security.auth.dto.response.LoginResponse;
import com.example.PMS.security.auth.dto.response.RegisterResponse;
import com.example.PMS.security.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                BaseResponse.ok("Login success.",response)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request
            ){
        RegisterResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.created("Register successful", response));
    }
}
