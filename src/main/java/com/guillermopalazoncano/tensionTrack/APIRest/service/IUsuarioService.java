/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.service;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import java.util.List;

/**
 *
 * @author guillermopalazoncano
 */
public interface IUsuarioService {
    Usuario registrar (Usuario usuario);
    Usuario modificar (Usuario usuario);
    List<Usuario> listar();
    Usuario obtener (Long id);
    boolean eliminar (Long id);
}
