package com.example.steam_clone;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
            .allowedOrigins("*") // 모든 도메인에서 접근 허용 (실제로는 필요에 따라 수정)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 지정
            .allowedHeaders("*"); // 모든 헤더 허용
    }
}

