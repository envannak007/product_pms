package com.example.PMS.security.auth.dto.response;

public record LoginResponse(
        String username,
        String token
) {
}
