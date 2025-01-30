package com.example.userservice.security.utils;

import com.example.userservice.security.constants.SecurityConstants;
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
 * @description JWT工具类
 */
@Component
public class JwtTokenUtils {

    public static String createToken(String username, String id, List<String> roles, Boolean isRememberMe) {
        if (isRememberMe == null) {
            isRememberMe = false;
        }
        long expiration = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String token = Jwts.builder()
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", roles))
                .setId(id)
                .setIssuer("xiao")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return SecurityConstants.TOKEN_PREFIX + token;
    }

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
