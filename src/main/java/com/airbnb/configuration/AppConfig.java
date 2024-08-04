package com.airbnb.configuration;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {



    private JwtFilter filter;
    private AuthenticationEntryPoint authenticationEntryPoint;

    public AppConfig(JwtFilter filter, AuthenticationEntryPoint authenticationEntryPoint) {
        this.filter = filter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    //this bean for security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(cs->cs.disable())
                .cors((cors)->cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((request)->
                        request.requestMatchers("/api/auth/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling((e)->e
                        .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement((s)->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                return httpSecurity.build();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfigurationSource corsConfigurationSource = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                corsConfiguration.setExposedHeaders(List.of("Authorization"));
                corsConfiguration.setMaxAge(3500L);
                return corsConfiguration;
            }
        };
        return corsConfigurationSource;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }



    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // Handle access denied exception
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied: from app config " + accessDeniedException.getMessage());
        };
    }

}
