package com.example.demo.service;

import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.authentication.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<Roles> getRolesByUsernameAndPassword(String username, String password) {
        return userRepository.findRolesByUsernameAndPassword(username, password);
    }
    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    public User getAuthenticatedUser(String username, String password) {
        Optional<User> optionalUser = getUserByUsernameAndPassword(username, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRoles(getRolesByUsernameAndPassword(username, password));
            return user;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
