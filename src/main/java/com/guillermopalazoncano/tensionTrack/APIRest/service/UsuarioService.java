/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.service;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.tensionTrack.APIRest.repository.IUsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author guillermopalazoncano
 */
@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    
    @Override
    public Usuario registrar(Usuario usuario) {
        return iUsuarioRepository.save(usuario);
    }

    @Override
    public Usuario modificar(Usuario usuario) {
        return iUsuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return iUsuarioRepository.findAll();
    }

    @Override
    public Usuario obtener(Long id) {
        Optional<Usuario> op = iUsuarioRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }

    @Override
    public boolean eliminar(Long id) {
        if (obtener(id)!=null) {
            iUsuarioRepository.deleteById(id);
            return true;
        } else
            return false;
    }
    
}
