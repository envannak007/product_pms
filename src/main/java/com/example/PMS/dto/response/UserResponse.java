package com.example.PMS.dto.response;

import java.time.LocalDateTime;

public record UserResponse (
        Integer id,
        String userName,
        String email,
        LocalDateTime createAt,
        LocalDateTime updateAt

){
}
