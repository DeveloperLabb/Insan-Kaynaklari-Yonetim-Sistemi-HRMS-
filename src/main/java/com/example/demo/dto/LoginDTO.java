package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String username;

    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
