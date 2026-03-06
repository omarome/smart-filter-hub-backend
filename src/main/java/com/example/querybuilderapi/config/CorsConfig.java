package com.example.querybuilderapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * CORS configuration for development.
 * In production, NGINX handles CORS, so this may not be needed.
 */
@Configuration
public class CorsConfig {

    @Value("${app.cors.allowed-origins:http://localhost:5173,http://localhost:3000}")
    private List<String> allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        // sanitize origins: strip trailing slash if present
        List<String> sanitized = allowedOrigins.stream()
                .map(o -> o.endsWith("/") ? o.substring(0, o.length()-1) : o)
                .toList();
        System.out.println("[CorsConfig] allowedOrigins=" + sanitized);

        CorsConfiguration config = new CorsConfiguration();
        if (sanitized.stream().anyMatch(o -> o.equals("*") || o.contains("*"))) {
            // wildcard present: use origin patterns instead of exact list
            config.setAllowedOriginPatterns(sanitized);
        } else {
            config.setAllowedOrigins(sanitized);
        }
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
