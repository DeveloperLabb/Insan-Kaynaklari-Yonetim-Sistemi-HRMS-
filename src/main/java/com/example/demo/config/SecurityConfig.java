package com.example.demo.config;

import com.example.demo.security.authentication.JwtAuthenticationFilter;
import com.example.demo.security.authentication.JwtBlacklistFilter;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtBlacklistFilter jwtBlacklistFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, JwtBlacklistFilter jwtBlacklistFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtBlacklistFilter = jwtBlacklistFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req-> req.requestMatchers("/login/**","/register/**","/","/dashboard","/register","user/register")
                                .permitAll()
                                .requestMatchers("/css/**", "/js/**", "/html/**") // Statik kaynak yolları
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).headers(headers -> headers
                        .addHeaderWriter((request, response) -> response.setHeader("X-Frame-Options", "ALLOWALL"))
                )
                .userDetailsService(userDetailsService)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtBlacklistFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, jwtBlacklistFilter.getClass())
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
