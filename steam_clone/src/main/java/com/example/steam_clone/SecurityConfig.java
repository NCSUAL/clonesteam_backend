package com.example.steam_clone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests((auth)->
                        auth.requestMatchers("/swagger-ui/index.html", "/swagger-ui/**", "/api/login", "/login-controller/login", "/api/hello").permitAll()
            );

        http
                .httpBasic((auth)-> auth.disable())
                .csrf((auth)-> auth.disable())
                .formLogin((auth)-> auth.disable());

        return http.build();
    }
}
