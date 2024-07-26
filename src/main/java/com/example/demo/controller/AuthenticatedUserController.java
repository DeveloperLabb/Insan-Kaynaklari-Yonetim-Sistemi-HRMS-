package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.security.authentication.JwtUtil;
import com.example.demo.service.PersonService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticatedUserController {

    private final PersonService personService;
    private User user;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticatedUserController(User user, UserService userService, JwtUtil jwtUtil, PersonService personService) {
        this.user = user;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.personService = personService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO userLogin) {
        UserDTO userDTO = new UserDTO();
        try{
            user.setUsername(userService.getAuthenticatedUser(userLogin.getUsername(), userLogin.getPassword()).getUsername());
            user.setPassword(userService.getAuthenticatedUser(userLogin.getUsername(), userLogin.getPassword()).getPassword());
            userDTO.setToken(jwtUtil.generateToken(user.getUsername(),user.getRoles()));
            userDTO.setMessage("Login successful");
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }catch (Exception e){
            userDTO.setMessage("Login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userDTO);
        }
    }
    /*
    @PostMapping("/denemeAuth")
    public User denemeAuth(@RequestBody LoginDTO userLogin){
        return userService.getAuthenticatedUser(userLogin.getUsername(), userLogin.getPassword());
    }

     */
}
