package com.example.steam_clone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/swagger-ui/index.html", "/swagger-ui/**", "/api/login", "/login-controller/login", "/api/hello").permitAll() 
            )
            .formLogin()
	        .and()
	        .csrf(csrf -> 
		        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		    );
        return http.build();
    }
}
