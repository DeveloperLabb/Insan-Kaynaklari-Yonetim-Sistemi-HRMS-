package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.security.authentication.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final User user;

    @Autowired
    public AuthenticationService(User user) {
        this.user = user;
    }

    //Controller sınıflarda autowired yapıp içlerinde token kontrolü yapıp ona göre Response atacağız.
    public boolean isAuthenticatedUser(UserDTO userDTO){
        if(userDTO.getToken().isBlank() || userDTO.getToken().equals(null)){
            return false;
        }
        else{
            if(JwtUtil.validateToken(userDTO.getToken(),user.getUsername())){
                return true;
            }
            return false;
        }
    }
}
