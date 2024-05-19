/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.service;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.tensionTrack.APIRest.dto.UsuarioRegistro;
import java.util.List;

/**
 *
 * @author guillermopalazoncano
 */
public interface IUsuarioService {
    Usuario registrar (UsuarioRegistro usuario);
    Usuario  modificar(Long idUsuario, String newPassword);
    List<Usuario> listar();
    Usuario obtener (Long id);
    Usuario obtenerPorNombreUsuario(String username);
    boolean eliminar (Long id);
}
