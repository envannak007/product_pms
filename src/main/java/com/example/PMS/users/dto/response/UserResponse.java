package com.example.PMS.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserResponse (
        Integer id,
        String userName,
        String email,
        @JsonFormat(pattern = "MMM dd, yyyy hh:mm a")
        LocalDateTime createAt,
        @JsonFormat(pattern = "MMM dd, yyyy hh:mm a")
        LocalDateTime updateAt

){
}
