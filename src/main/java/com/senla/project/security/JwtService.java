package com.senla.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigninigKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public SecretKey getJwtSigninigKey(){
        return Keys.hmacShaKeyFor(jwtSigninigKey.getBytes(StandardCharsets.UTF_8));    }

    public String extractUserName(String token){
        return extractClaims(token).getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("role", role);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +  expirationTime))
                .signWith(getJwtSigninigKey())
                .compact();
    }

    public Date extractExpiration(String token){
        return extractClaims(token).getExpiration();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getJwtSigninigKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Boolean isTokenUsernameValid(String token, UserDetails userDetails){
        String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        return !isTokenExpired(token) && isTokenUsernameValid(token, userDetails);
    }
}
