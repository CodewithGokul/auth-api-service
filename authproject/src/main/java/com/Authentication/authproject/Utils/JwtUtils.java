package com.Authentication.authproject.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {
    public static String SECRET_KEY = "015e33484acd7f806e3fbd5cb9e137c084f92fb8870db28efec0b3a5d7ed08d5";

    public String JwtGenerate(UserDetails userDetails) {


        HashMap<String, String> claims = new HashMap<>();
        //Generate Jwt Token using Claims
        return Jwts.builder().claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(getKey())  //Signature Part
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Claims getClaims(String Token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(Token)
                .getPayload();
        return claims;
    }

    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().before(Date.from(Instant.now()));
    }

    public boolean validToken(String Token,UserDetails userDetails){
        String tokenName = extractUsername(Token);

        return (tokenName.equals(userDetails.getUsername()) && !isExpired(Token));
    }
}
