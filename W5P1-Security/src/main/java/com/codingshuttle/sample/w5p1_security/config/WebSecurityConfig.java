package com.codingshuttle.sample.w5p1_security.config;

import com.codingshuttle.sample.w5p1_security.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codingshuttle.sample.w5p1_security.entities.enums.Role.ADMIN;
import static com.codingshuttle.sample.w5p1_security.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF as it's unnecessary for token-based auth
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless sessions
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll() // Allow unauthenticated GET
                        .requestMatchers(HttpMethod.POST, "/posts/**").authenticated() // Require authentication for POST
                        .anyRequest().authenticated()) // Default: any other request must be authenticated
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Enable HTTP Basic Authentication // Manage sessions

        return httpSecurity.build();
    }

//    // Use this code incase you want to load user login from InMemory
//    // Ensure that we are not using UserService class incase use InMemoryUserDetails
//    @Bean
//    UserDetailsService InMemoryUserDetailsService() {
//
//        UserDetails adminUser = User
//                .withUsername("user")
//                .password(passwordEncoder()
//                        .encode("user@1234"))
//                .roles("ADMIN").build();
//
//        UserDetails normalUser = User
//                .withUsername("admin")
//                .password(passwordEncoder()
//                        .encode("admin@1234"))
//                .roles("USER").build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
