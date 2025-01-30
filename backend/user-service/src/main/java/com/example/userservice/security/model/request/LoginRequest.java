package com.example.userservice.security.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author xiao
 * @description 用户登录请求DTO
 */
@Data
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
    private Boolean rememberMe;
}
