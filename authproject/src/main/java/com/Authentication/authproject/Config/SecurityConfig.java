package com.Authentication.authproject.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    @Autowired
    AppConfig appConfig;
    @Autowired
    JwtConfig jwtConfig;
        @Bean
        public SecurityFilterChain filter(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth->{
                            auth.requestMatchers("/api/users/**").permitAll();
                            auth.anyRequest().authenticated();
                    })
                    .sessionManagement(session->session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS ))
                    .authenticationProvider(appConfig.authenticationProvider())
                    .addFilterBefore(jwtConfig, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }


}
