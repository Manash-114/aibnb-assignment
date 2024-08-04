package com.airbnb.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtHelper {
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    public String generateToken(Authentication authentication){

        String jwt = Jwts.builder().setIssuer("manashjyoti")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+3600000))
                .claim("username",authentication.getName())
                .signWith(key)
                .compact();
        return  jwt;
    }

    public String getUserNameFromToken(String jwtToken) throws Exception{
        jwtToken = jwtToken.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
        String email = String.valueOf(claims.get("username"));
        return email;
    }



}
