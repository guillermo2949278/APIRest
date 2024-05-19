/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.service;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.tensionTrack.APIRest.dto.UsuarioRegistro;
import com.guillermopalazoncano.tensionTrack.APIRest.repository.IUsuarioRepository;
import com.guillermopalazoncano.tensionTrack.APIRest.security.PasswordEncoderConfig;
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
    
    @Autowired
    private PasswordEncoderConfig pec;
    
    @Override
    public Usuario registrar(UsuarioRegistro usuarioRegistrar) {
        if (usuarioRegistrar.getPassword().equals(usuarioRegistrar.getPasswordVerificacion())){
            Usuario usuario = new Usuario(usuarioRegistrar.getUsername(),
                                          pec.passwordEncoder().encode(usuarioRegistrar.getPassword()));
            return iUsuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    @Override
    public Usuario modificar(Long idUsuario, String newPassword) {
        Optional<Usuario> op = iUsuarioRepository.findById(idUsuario);
        if (op.isPresent()){
            Usuario usu = op.get();
            usu.setPassword(pec.passwordEncoder().encode(newPassword));
            return iUsuarioRepository.save(usu);
        } else {
            return null;
        }
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
        if (iUsuarioRepository.existsById(id)) {
            iUsuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Usuario obtenerPorNombreUsuario(String username) {
        Optional<Usuario> op = iUsuarioRepository.findByUsername(username);
        return op.isPresent() ? op.get() : null;
    }
    
    
    
}
