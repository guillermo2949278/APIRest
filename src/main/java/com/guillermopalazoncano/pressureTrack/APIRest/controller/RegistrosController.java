/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.controller;

import com.guillermopalazoncano.pressureTrack.APIRest.dto.RegistroCrear;
import com.guillermopalazoncano.pressureTrack.APIRest.model.Registros;
import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.pressureTrack.APIRest.service.RegistrosService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author guillermopalazoncano
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/registros")
public class RegistrosController {

    @Autowired
    private final RegistrosService registrosService;

    @GetMapping("/")
    public ResponseEntity<List<Registros>> getRegistrosUsuario(@AuthenticationPrincipal Usuario usuario) {
        List<Registros> resultados = registrosService.obtenerPorUserId(usuario.getUserId());
        return new ResponseEntity<>(resultados, HttpStatus.OK);

    }
    
    @PostMapping("/")
    public ResponseEntity<?> postRegistro(@AuthenticationPrincipal Usuario usuario,
                                                  @RequestBody RegistroCrear rc){
        Registros registro = registrosService.registrar(usuario.getUserId(), rc);
        if (registro != null){
            return ResponseEntity.status(HttpStatus.CREATED)
               .body(registro);
        } else {
            return new ResponseEntity<>("Error al crear el registro", HttpStatus.BAD_REQUEST);
        }
    }
    
    // Entendemos que en la aplicación correspondiente han explicado el riesgo de borrar una cuenta.
    @DeleteMapping("/borraregistro/{id}")
    public ResponseEntity<String> deleteRegistro(@AuthenticationPrincipal Usuario usuarioLogueado,
                                                    @PathVariable Integer id){
        // Obtengo el registro para comprobar si es del usuario logueado.
        Registros registro = registrosService.obtener(id);
        if (registro == null){
            return new ResponseEntity<>("No se ha encontrado el registro",HttpStatus.NOT_FOUND);
        } else {
            // Solamente borramos si el usuario del Registro es el usuario del Token (Usuario logueado).
            if (registro.getUserId().equals(usuarioLogueado.getUserId())){
                registrosService.eliminar(id); 
                return new ResponseEntity<>("Registro eliminado correctamente", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("El registro a borrar no pertenece al Usuario",HttpStatus.FORBIDDEN);
            }
        }
    }
    
    
   
    
   
}
