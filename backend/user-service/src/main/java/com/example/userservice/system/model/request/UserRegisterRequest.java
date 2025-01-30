package com.example.userservice.system.model.request;


import com.example.userservice.system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xiao
 */
@Data
public class UserRegisterRequest {
    @NotBlank
    @Size(max = 64, message = "user name max 64")
    private String userName;
    @NotBlank
    @Size(max = 64, message = "password max 64")
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User()
                .setUserName(this.getUserName())
                .setPassword(passwordEncoder.encode(this.getPassword()))
                .setEnabled(true);
    }
}
