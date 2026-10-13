package com.senla.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter, AuthenticationProvider provider, RestAuthenticationEntryPoint authenticationEntryPoint, RestAccessDeniedHandler accessDeniedHandler) {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(provider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clients").permitAll()
                        .requestMatchers(HttpMethod.GET, "/barbers/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/services/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schedules/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/appointments/**").hasAnyAuthority("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/reviews/**").hasAuthority("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/appointments/client/**").hasAnyAuthority("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clients/**").hasAnyAuthority("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/appointments/**").hasAnyAuthority("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/reviews/**").hasAuthority("CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/appointments/**").hasAnyAuthority("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").hasAnyAuthority("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/schedules/**").hasAuthority("BARBER")
                        .requestMatchers(HttpMethod.PUT, "/schedules/**").hasAuthority("BARBER")
                        .requestMatchers(HttpMethod.DELETE, "/schedules/**").hasAuthority("BARBER")
                        .requestMatchers(HttpMethod.PUT, "/barbers/**").hasAnyAuthority("BARBER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/appointments/barber/**").hasAnyAuthority("ADMIN", "BARBER")
                        .requestMatchers(HttpMethod.GET, "/clients/barber/**").hasAnyAuthority("ADMIN", "BARBER")
                        .requestMatchers(HttpMethod.PUT, "/appointments/*/status").hasAuthority("ADMIN")
                        .requestMatchers("/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/roles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/services/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/services/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/services/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clients").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clients/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/barbers/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clients/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
