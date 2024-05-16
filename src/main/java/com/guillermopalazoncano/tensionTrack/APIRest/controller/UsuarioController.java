/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.controller;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
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
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id){
        Usuario usu = usuarioService.obtener(id);
        if (usu != null){
            return new ResponseEntity<>(usu,HttpStatus.OK);            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/id")
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usu){
        Usuario usuario = usuarioService.registrar(usu);
        return new ResponseEntity<>(usuario,HttpStatus.CREATED);            
    }
    
    @PutMapping("/id/{id}")
    public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usu,
                                              @PathVariable Long id){
        
        Usuario usuario = usuarioService.obtener(id);
        if (usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (usu.getUserId()!= null && usu.getUserId()!=id){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                usuario.setUsername(usu.getUsername());
                usuario.setPassword(usu.getPassword());
                usuario = usuarioService.modificar(usuario);
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }
        }
    }
    
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.obtener(id);
        if (usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
