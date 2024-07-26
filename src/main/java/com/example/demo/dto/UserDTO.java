package com.example.demo.dto;

import com.example.demo.model.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String token;
    @NotEmpty()
    private String message;
}
