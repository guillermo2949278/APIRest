/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.security.jwt;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 *
 * @author guillermopalazoncano
 */
@Log
@Service
public class JwtProvider {
    
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    // Se añade al principio del token de manera manual y luego sirve para comprobar el token
    public static final String TOKEN_PREFIX = "Bearer ";
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.duration}")
    private String jwtVidaEnDias;
    
    private JwtParser jwtParser;
    
    private SecretKey secretKey;
    
    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        
        // Producir y validar token.
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }
    
    public String generateToken(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date fechaExpiracion = Date.from(
                LocalDateTime
                        .now()
                        .plusDays(Integer.parseInt(jwtVidaEnDias))
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        
        // Construimos el token que proporcionaremos al usuario en el endpoint del login.
        return Jwts.builder()                
                .header().add("typ", TOKEN_HEADER).and()
                .subject(usuario.getUserId().toString())
                .issuedAt(new Date())
                .expiration(fechaExpiracion)
                .signWith(secretKey)
                .compact();
    }
    
    public boolean validateToken (String token){
        
       try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                IllegalArgumentException | UnsupportedJwtException ex){
            log.info("Error con el token "+ex.getMessage());
        }
        return false;
    }
    
    public Long getUserIdFromJwtToken(String token){
        return Long.valueOf(jwtParser.parseSignedClaims(token).getPayload().getSubject());
    }
    
}
