package com.example.PMS.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Name is required.")
        @Size(min = 2,max = 100,message = "Username must be between 2 and 100 characters.")
        String userName,

        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 6,max = 20,message = "Password must be between 8 and 100 characters.")
        String password
) {
}
