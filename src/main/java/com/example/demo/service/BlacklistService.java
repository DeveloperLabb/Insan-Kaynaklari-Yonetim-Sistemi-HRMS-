package com.example.demo.service;
import com.example.demo.model.BlacklistToken;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    @Transactional
    public void blacklistToken(String token) {
        BlacklistToken blacklistToken = new BlacklistToken(token, LocalDateTime.now());
        blacklistRepository.save(blacklistToken);
    }

    public boolean isTokenBlacklisted(String token) {
        Optional<BlacklistToken> blacklistedToken = blacklistRepository.findByToken(token);
        return blacklistedToken.isPresent();
    }
}

