package com.example.PMS.category.dto.response;

import java.time.LocalDateTime;

public record CategoryResponse (
        Integer id,
        String name,
        String description,
        LocalDateTime createAt,
        LocalDateTime updateAt
){
}
