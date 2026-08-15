package com.lovefound.love_found_api.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF because we use JWT
            .csrf(AbstractHttpConfigurer::disable)

            // No HTTP Session
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Authorization Rules
            .authorizeHttpRequests(auth -> auth

                // ---------- Public ----------
                .requestMatchers("/api/auth/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/pets/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/kind-map/**").permitAll()
                .requestMatchers("/error").permitAll()

                // ---------- Shelter ----------
                .requestMatchers(HttpMethod.POST, "/api/pets/**").hasRole("SHELTER")
                .requestMatchers(HttpMethod.PUT, "/api/pets/**").hasRole("SHELTER")
                .requestMatchers(HttpMethod.DELETE, "/api/pets/**").hasRole("SHELTER")

                .requestMatchers("/api/applications/shelter/**")
                .hasRole("SHELTER")

                // ---------- Adopter ----------
                .requestMatchers(HttpMethod.POST, "/api/applications/**")
                .hasRole("ADOPTER")

                .requestMatchers("/api/applications/my-applications")
                .hasRole("ADOPTER")

                // ---------- Admin ----------
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // JWT Filter
            .addFilterBefore(jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

}
