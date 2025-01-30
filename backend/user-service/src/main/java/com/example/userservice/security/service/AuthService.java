package com.example.userservice.security.service;

import com.example.userservice.security.entity.JwtUser;
import com.example.userservice.security.model.request.LoginRequest;
import com.example.userservice.security.utils.CurrentUserUtils;
import com.example.userservice.security.utils.JwtTokenUtils;
import com.example.userservice.system.entity.User;
import com.example.userservice.system.exception.UserNameNotFoundException;
import com.example.userservice.system.repository.UserRepository;
import com.example.userservice.system.service.UserService;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiao
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;
    private final UserRepository userRepository;

    public String createToken(LoginRequest loginRequest) {
//        User user = userService.find(loginRequest.getUserName());
        String userName = loginRequest.getUserName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of("userName:", userName)));
        if (!userService.check(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("The user name or password is not correct.");
        }
        JwtUser jwtUser = new JwtUser(user);
        if (!jwtUser.isEnabled()) {
            throw new BadCredentialsException("User is not enabled.");
        }
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtTokenUtils.createToken(user.getUserName(), user.getId().toString(), authorities, loginRequest.getRememberMe());
        stringRedisTemplate.opsForValue().set(user.getId().toString(), token);
        return token;
    }

    public void removeToken() {
        stringRedisTemplate.delete(currentUserUtils.getCurrentUser().getId().toString());
    }

}
