/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.dto;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author guillermopalazoncano
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class JwtUsuarioRespuesta extends UsuarioRespuesta {
    private String token;
    
    public JwtUsuarioRespuesta (UsuarioRespuesta usuarioRespuesta){
        super(usuarioRespuesta.getUserId(), usuarioRespuesta.getUsername());        
    }
    
    public static JwtUsuarioRespuesta obtenerJwtUsuarioRespuesta (Usuario usuario, String token){
        JwtUsuarioRespuesta resultado = new JwtUsuarioRespuesta(
                new UsuarioRespuesta(usuario.getUserId().toString(), usuario.getUsername()));
        resultado.setToken(token);        
        return resultado;
    }
}
