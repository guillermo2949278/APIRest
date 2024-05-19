/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.dto;
import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import lombok.*;
/**
 *
 * @author guillermopalazoncano
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class UsuarioRespuesta {

    protected String userId;
    protected String username;

    public static UsuarioRespuesta usuarioRespuestaDeUsuario(Usuario usu) {

        return new UsuarioRespuesta(usu.getUserId().toString(), usu.getUsername());
    }

}
