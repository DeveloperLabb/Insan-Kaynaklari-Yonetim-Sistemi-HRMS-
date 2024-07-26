package com.example.demo.config;

import com.example.demo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public User authorizedUser(){
        return new User();
    }
}
