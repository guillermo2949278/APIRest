/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.controller;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.tensionTrack.APIRest.dto.*;
import com.guillermopalazoncano.tensionTrack.APIRest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author guillermopalazoncano
 */
@RestController
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioRespuesta> getUsuario(@PathVariable Long id){
        Usuario usu = usuarioService.obtener(id);
        if (usu != null){
            return new ResponseEntity<>(UsuarioRespuesta.usuarioRespuestaDeUsuario(usu),HttpStatus.OK);            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/auth/registro")
    public ResponseEntity<?> postUsuario(@RequestBody UsuarioRegistro usu){
        Usuario usuario = usuarioService.registrar(usu);
        if (usuario!= null){
            return new ResponseEntity<>(UsuarioRespuesta.usuarioRespuestaDeUsuario(usuario),HttpStatus.CREATED);            
        } else {
            return new ResponseEntity<>("Las contraseñas no coinciden", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/id/{id}")
    public ResponseEntity<UsuarioRespuesta> putPasswordUsuario(@RequestBody Usuario usu,
                                              @PathVariable Long id){
        
        Usuario usuario = usuarioService.obtener(id);
        if (usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (usu.getUserId()!= null && usu.getUserId()!=id){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                usuario.setPassword(usu.getPassword());
                usuario = usuarioService.modificar(id, usu.getPassword());
                return new ResponseEntity<>(UsuarioRespuesta.usuarioRespuestaDeUsuario(usuario), HttpStatus.OK);
            }
        }
    }
    
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.obtener(id);
        if (usuario == null){
            return new ResponseEntity<>("No se ha encontrado el usuario",HttpStatus.NOT_FOUND);
        } else {
            usuarioService.eliminar(id);
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.NO_CONTENT);
        }
    }
}
