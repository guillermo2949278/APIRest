/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.security.jwt;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.pressureTrack.APIRest.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author guillermopalazoncano
 */
@Log
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private final UsuarioService usuarioService;
    
    @Autowired
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        // Extraemos el token de la petición.
        String token = getJwtTokenFromRequest(request);
        try {
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)){
                Long userId = jwtProvider.getUserIdFromJwtToken(token);
                Usuario usuario = usuarioService.obtener(userId);                
                if (usuario!=null){
                    
                    // Objeto de tipo authentication (UsernamePasswordAuthenticationToken)
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    usuario, null,usuario.getAuthorities());
                    
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    
                    // El token es válido y el usuario asociado a él también, 
                    // construimos el objeto de autenticación y lo establecemos al contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                
            }
        } catch (Exception ex){
            log.info("Error de autenticación usando el token JWT");
        }
        
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)){
            // nos quedamos con lo que viene después de bearer.
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }
        return null;
    }
    
}
