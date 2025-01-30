package com.example.configservice.utils;

import com.example.configservice.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiao
 */
@Component
public class JwtTokenUtils {


    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    public static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String role = (String) claims.get(SecurityConstants.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    private static SecretKey generateSecretKey() {
//        // length means (32 bytes are required for 256-bit key)
//        int length = 32;
//
//        // Create a secure random generator
//        SecureRandom secureRandom = new SecureRandom();
//
//        // Create a byte array to hold the random bytes
//        byte[] keyBytes = new byte[length];
//
//        // Generate the random bytes
//        secureRandom.nextBytes(keyBytes);
//
//        // Encode the key in Base64 format for easier storage and usage
//        String base64Str = Base64.getEncoder().encodeToString(keyBytes);
//        byte[] base64Bytes = DatatypeConverter.parseBase64Binary(base64Str);
//        return Keys.hmacShaKeyFor(base64Bytes);
//    }

    private SecretKey generateSecretKey(String secretKey) {
        return Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(secretKey));
    }

    @Value("${app.security.jwt.secret-key}")
    private String JwtSecretKey;
    private static SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {
        SECRET_KEY = generateSecretKey(JwtSecretKey);
    }


}
