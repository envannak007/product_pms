package com.example.PMS.security.auth.dto.response;

public record RegisterResponse(
        Integer id,
        String username,
        String email,
        String message
) {

}
