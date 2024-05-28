/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.security;

import com.guillermopalazoncano.pressureTrack.APIRest.security.errorHandling.JwtAccessDeniedHandler;
import com.guillermopalazoncano.pressureTrack.APIRest.security.errorHandling.JwtAuthenticationEntryPoint;
import com.guillermopalazoncano.pressureTrack.APIRest.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author guillermopalazoncano
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    
    @Autowired
    private final UserDetailsService userDetailsService;
    
    @Autowired 
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/auth/registro", 
                "auth/login", "/fecha");        
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        
        AuthenticationManager authenticationManager =
                authenticationManagerBuilder
                        .authenticationProvider(authenticationProvider())
                        .build();
        return authenticationManager;                        
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        
        return authenticationProvider;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
                // deshabilito csrf  
                .csrf(csrf -> csrf.disable())
                // Autorización de solicitudes. Deben estar autenticados.
                .authorizeHttpRequests(auth -> auth
                    //.requestMatchers("*****").hasRole("ADMIN")
                    .anyRequest().authenticated())
                // manejo de excepciones y errores
                .exceptionHandling(exceptionHandling -> 
                    exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))   
                // No mantenemos el estado de la sesión al ser una API Rest
                .sessionManagement(sessionManagement -> 
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));                                                  
                
          http.addFilterBefore(jwtAuthenticationFilter, 
                  UsernamePasswordAuthenticationFilter.class);
          
          
          return http.build();
    }
    
    
    
}
