package com.leonardo.bookstoremanager.service;

import com.google.common.base.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenManager {

    private Long jwtTokenValidity;

    private String secret;

    public JwtTokenManager(
            @Value("${jwt.validity}") Long jwtTokenValidity,
            @Value("${jwt.secret}") String secret) {
        this.jwtTokenValidity = jwtTokenValidity;
        this.secret = secret;
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(userDetails.getUsername(), claims);
    }

    private String doGenerateToken(String userName, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims).setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String gerUserNameFromToken(String token) {
        return gerClaimFromToken(token, Claims::getSubject);
    }

    public Date gerExpirationDateFromToken(String token) {
        return gerClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T gerClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsForToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsForToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String userName = gerUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = gerExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
}
