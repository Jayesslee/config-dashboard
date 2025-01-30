package com.example.configservice.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiao
 */
@Component
public final class SecurityConstants {

    /**
     * Role Key in Claim
     **/
    public static final String ROLE_CLAIMS = "rol";


    public static final long EXPIRATION = 60 * 60L;


    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    // Swagger WHITELIST
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            "/doc.html",
    };

    // System WHITELIST
//    public static final String[] SYSTEM_WHITELIST = {
//            "/api/auth/login",
//            "/api/user/register"
//    };

    private SecurityConstants() {
    }

}
