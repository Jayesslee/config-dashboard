package com.example.userservice.security.config;

import com.example.userservice.security.constants.SecurityConstants;
import com.example.userservice.security.exception.JwtAccessDeniedHandler;
import com.example.userservice.security.exception.JwtAuthenticationEntryPoint;
import com.example.userservice.security.filter.JwtAuthorizationFilter;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * @author xiao
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final StringRedisTemplate stringRedisTemplate;

    public SecurityConfiguration(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                // CORS config
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // restful service csrf disabled
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // permit specified routes on whitelist
                        .requestMatchers(SecurityConstants.SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityConstants.SYSTEM_WHITELIST).permitAll()
                        // other routes need to be authenticated
                        .anyRequest().authenticated()
                )
                // add customized JWT Filter
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager, stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
                // restful service session set to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        // 401 authentication failed
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        // 403 authorization failed
                        .accessDeniedHandler(new JwtAccessDeniedHandler())
                )
                .build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()));
        configuration.setExposedHeaders(ImmutableList.of(SecurityConstants.TOKEN_HEADER));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}