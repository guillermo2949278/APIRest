/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.service;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.pressureTrack.APIRest.dto.UsuarioCrear;
import com.guillermopalazoncano.pressureTrack.APIRest.model.Registros;
import com.guillermopalazoncano.pressureTrack.APIRest.repository.IRegistrosRepository;
import com.guillermopalazoncano.pressureTrack.APIRest.repository.IUsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private IRegistrosRepository iRegistrosRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Override
    public Usuario registrar(UsuarioCrear usuarioRegistrar) {
        // Compruebo si existe ya un usuario con ese Username (mail). Si existe devuelvo null
        if (existsByUsername(usuarioRegistrar.getUsername())){
            return null;
        }
        if (usuarioRegistrar.getPassword().equals(usuarioRegistrar.getPasswordVerificacion())){
            Usuario usuario = new Usuario(usuarioRegistrar.getUsername(),
                                          passwordEncoder.encode(usuarioRegistrar.getPassword()));
            return iUsuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    @Override
    public Usuario modificar(Long idUsuario, String newPassword, String verifyPassword) {
        Optional<Usuario> op = iUsuarioRepository.findById(idUsuario);
        if (op.isPresent()){
            Usuario usu = op.get();
            usu.setPassword(passwordEncoder.encode(newPassword));
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
            List<Registros> listaRegistrosUsuario = iRegistrosRepository.findByUserId(id);
            for (Registros r: listaRegistrosUsuario) {
                iRegistrosRepository.deleteById(r.getRegistroId());
            }
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
    
    public boolean isClaveUsuario(Usuario usuario, String claveClaro){
        return passwordEncoder.matches(claveClaro, usuario.getPassword());
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return iUsuarioRepository.existsByUsername(username);
    }
}
