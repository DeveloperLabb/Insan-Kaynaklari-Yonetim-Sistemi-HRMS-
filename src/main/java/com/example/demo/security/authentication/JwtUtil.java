package com.example.demo.security.authentication;
import com.example.demo.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.Role;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {

    // Güvenli bir anahtar oluşturur (HS256 için)
    private final String SECRET_KEY = "c8000bd8b8fa0929cce205c0f1a7e8bfba9934bbfcab9e509aa4ae2c8177a672";
    private final Date EXPIRATION_TIME = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
    // JWT oluşturma
    public String generateToken(User user) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(EXPIRATION_TIME)
                .signWith(getSigninKey())
                .compact();
        return token;
    }
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT'den iddiaları (claims) çıkarma
    private Claims extractAllClaims(String token) throws MalformedJwtException, ExpiredJwtException, UnsupportedJwtException,SignatureException {
        try{
            return Jwts.parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch(Exception e){
            throw e;
        }
    }
    public String extractUsername(String token) {
        try{
            return extractClaim(token,Claims::getSubject);
        }catch (Exception e){
            throw e;
        }
    }

    public boolean isValid(String token, UserDetails user) {
        try{
            String username = extractUsername(token);
            return username.equals(user.getUsername()) && !isTokenExpired(token);
        }catch(Exception e){
            throw e;
        }
    }
    public <T> T extractClaim(String token, Function<Claims,T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    // JWT geçerliliğini kontrol etme
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

}
