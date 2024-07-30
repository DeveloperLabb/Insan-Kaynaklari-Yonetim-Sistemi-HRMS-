package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserDTO {

    private String token;
    @NotEmpty()
    private String message;
}
