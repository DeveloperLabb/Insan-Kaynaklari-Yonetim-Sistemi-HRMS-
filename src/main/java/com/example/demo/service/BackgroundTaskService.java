package com.example.demo.service;

import com.example.demo.model.BlacklistToken;
import com.example.demo.repository.BlacklistRepository;
import com.example.demo.security.authentication.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackgroundTaskService {
    private final BlacklistRepository blacklistRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public BackgroundTaskService(BlacklistRepository blacklistRepository, JwtUtil jwtUtil) {
        this.blacklistRepository = blacklistRepository;
        this.jwtUtil = jwtUtil;
    }
    //@Scheduled(fixedRate = 60000)
    @Scheduled(cron = "0 40 9 * * *")
    public void performBlacklistDeadTokensDeletion() {
        List<BlacklistToken> blacklistTokens = blacklistRepository.findAll();
        int counter = 0;
        for (BlacklistToken blacklistToken : blacklistTokens) {
            if(jwtUtil.isTokenExpired(blacklistToken.getToken())){
                blacklistRepository.delete(blacklistToken);
                counter++;
            }
        }
        System.out.println("Blacklist dead tokens deleted :"+ counter);
    }
}
