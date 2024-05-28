/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.service;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.pressureTrack.APIRest.dto.UsuarioCrear;
import java.util.List;

/**
 *
 * @author guillermopalazoncano
 */
public interface IUsuarioService {
    Usuario registrar (UsuarioCrear usuario);
    Usuario  modificar(Long idUsuario, String newPassword, String verifyPassword);
    List<Usuario> listar();
    Usuario obtener (Long id);
    Usuario obtenerPorNombreUsuario(String username);
    boolean eliminar (Long id);
    
    boolean existsByUsername(String username);
}
