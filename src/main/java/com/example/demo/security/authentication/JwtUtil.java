package com.example.demo.security.authentication;
import com.example.demo.model.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    // Güvenli bir anahtar oluşturur (HS256 için)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT oluşturma
    public static String generateToken(String username, List<Roles> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 saat geçerli
                .signWith(key)
                .compact();
    }

    // JWT'den iddiaları (claims) çıkarma
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT geçerliliğini kontrol etme
    public static boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // JWT'yi doğrulama
    public static boolean validateToken(String token, String username) {
        final String tokenUsername = extractClaims(token).getSubject();
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}
