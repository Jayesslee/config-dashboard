package com.example.userservice.system.model.request;


import com.example.userservice.system.entity.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xiao
 */
@Data
public class UserUpdateRequest {
    @NotBlank
    @Size(max = 64, message = "user name max 64")
    private String userName;
    @Size(max = 64, message = "password max 64")
    private String password;
    private Boolean enabled;

}
