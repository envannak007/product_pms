package com.example.PMS.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record OrderRequest (

        @NotNull(message = "Total amount is required.")
        @DecimalMin(value = "0.0",inclusive = false,message = "Total amount must be greater then 0")
        BigDecimal totalAmount,

        @NotBlank(message = "Status is required.")
        @Size(max = 50,message = "Status must not exceed 50 characters")
        String status,

        @NotNull(message = "User id is required.")
        Integer userId
){
}
