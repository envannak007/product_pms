package com.example.PMS.security.auth.mapper;

import com.example.PMS.security.auth.dto.response.LoginResponse;
import com.example.PMS.security.auth.dto.response.RegisterResponse;
import com.example.PMS.security.jwt.JwtService;
import com.example.PMS.users.entitty.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {
    private final JwtService jwtService;
    public LoginResponse toLoginResponse(UserDetails userDetails,String token){

        return new LoginResponse(
                userDetails.getUsername(),
                token
        );
    }

    public RegisterResponse toRegisterResponse (UserEntity user){
        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                "Register successful"
        );
    }
}
