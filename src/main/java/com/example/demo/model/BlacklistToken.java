package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "blacklist-tokens")
@Data
@NoArgsConstructor
public class BlacklistToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    private LocalDateTime blacklistedAt;


    public BlacklistToken(String token, LocalDateTime blacklistedAt) {
        this.token = token;
        this.blacklistedAt = blacklistedAt;
    }

}
