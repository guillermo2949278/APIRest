/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.controller;

import com.guillermopalazoncano.pressureTrack.APIRest.dto.UsuarioLogin;
import com.guillermopalazoncano.pressureTrack.APIRest.dto.UsuarioCrear;
import com.guillermopalazoncano.pressureTrack.APIRest.dto.UsuarioRespuesta;
import com.guillermopalazoncano.pressureTrack.APIRest.dto.JwtUsuarioRespuesta;
import com.guillermopalazoncano.pressureTrack.APIRest.dto.UsuarioCambioClave;
import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.pressureTrack.APIRest.security.jwt.JwtProvider;
import com.guillermopalazoncano.pressureTrack.APIRest.service.UsuarioService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author guillermopalazoncano
 */
@RestController
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;
    
    @Autowired
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private final JwtProvider jwtProvider;
    
    // Prueba que no necesita usuario logueado
    @GetMapping("/fecha")
    public String getFecha(){
        return fechaActual();
    }
    
    // Prueba que necesita usuario logueado.
    @GetMapping("/fecha2")
    public String getFecha2(){
        return fechaActual();
    }
    
    /* No recuperamos datos con id, ya que es en el login donde obtenemos los datos del mismo.
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioRespuesta> getUsuario(@PathVariable Long id){
        Usuario usu = usuarioService.obtener(id);
        if (usu != null){
            return new ResponseEntity<>(UsuarioRespuesta.usuarioRespuestaDeUsuario(usu),HttpStatus.OK);            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    */
    
    @PostMapping("/auth/registro")
    public ResponseEntity<JwtUsuarioRespuesta> postUsuario(@RequestBody UsuarioCrear usu){
        Usuario usuario = usuarioService.registrar(usu);
        if (usuario!= null){
            // Realizamos el proceso de login
            Authentication authentication = 
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        usu.getUsername(),
                        usu.getPassword()                        
                    )      
                );  
            
             // Una vez realizada la autenticación se guarda.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        
            // Debemos devolver el token creado para que pueda utilizarlo en otras peticiones
            String token = jwtProvider.generateToken(authentication);
        
            // Obtenemos el usuario y con el token ya generamos la respuesta a devolver
            Usuario usuAutenticacion = (Usuario) authentication.getPrincipal();

            
             return ResponseEntity.status(HttpStatus.CREATED)
               .body(JwtUsuarioRespuesta.obtenerJwtUsuarioRespuesta(usuAutenticacion, token));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en el cambio de contraseña");
        }
        
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUsuarioRespuesta> login(@RequestBody UsuarioLogin usuarioLogin){
        
        // Realizamos la autenticación
        Authentication authentication = 
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        usuarioLogin.getUsername(),
                        usuarioLogin.getPassword()                        
                    )      
                );        
        // Una vez realizada la autenticación se guarda.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Debemos devolver el token creado para que pueda utilizarlo en otras peticiones
        String token = jwtProvider.generateToken(authentication);
        
        // Obtenemos el usuario y con el token ya generamos la respuesta a devolver
        Usuario usu = (Usuario) authentication.getPrincipal();
        
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(JwtUsuarioRespuesta.obtenerJwtUsuarioRespuesta(usu, token));
  
        
    }
    
    @PutMapping("/usuario/cambioclave")
    public ResponseEntity<UsuarioRespuesta> changePassword(@RequestBody 
            UsuarioCambioClave usuarioCambioClave,
            @AuthenticationPrincipal Usuario usuarioLogueado){
        
        try {
            // Comprobamos si la contraseña que nos indica que tiene actualmente es correcta.
            if (usuarioService.isClaveUsuario(usuarioLogueado, 
                    usuarioCambioClave.getOldPassword())){
                // Si la clave que ha pasado y la de verificación es correcta se modifica correctamente.
                Usuario usuario = usuarioService.modificar(usuarioLogueado.getUserId(), 
                        usuarioCambioClave.getNewPassWord(), usuarioCambioClave.getVerifyNewPassWord());
                if (usuario!= null){
                    return new ResponseEntity<>(UsuarioRespuesta.usuarioRespuestaDeUsuario(usuario),HttpStatus.OK);            
                } else {
                    throw new RuntimeException();
                }
            }
                
        } catch (RuntimeException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en el cambio de contraseña");
        }
        return null;        
    }
    
    // Entendemos que en la aplicación correspondiente han explicado el riesgo de borrar una cuenta.
    @DeleteMapping("/borrausuario")
    public ResponseEntity<String> deleteUsuario(@AuthenticationPrincipal Usuario usuarioLogueado){
        Usuario usuario = usuarioService.obtener(usuarioLogueado.getUserId());
        if (usuario == null){
            return new ResponseEntity<>("No se ha encontrado el usuario",HttpStatus.NOT_FOUND);
        } else {
            usuarioService.eliminar(usuario.getUserId()); // Elimina también sus registros.
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.NO_CONTENT);
        }
    }
    
    
    
    
    private String fechaActual(){
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Formato que nos interesa
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        String fechaFormateada = fechaHoraActual.format(formatoFecha);
        String horaFormateada = fechaHoraActual.format(formatoHora);

        return fechaFormateada+ " - "+horaFormateada;
    }
}
