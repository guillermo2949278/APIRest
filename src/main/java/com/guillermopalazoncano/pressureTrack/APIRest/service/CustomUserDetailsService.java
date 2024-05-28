/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.service;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.pressureTrack.APIRest.repository.IUsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author guillermopalazoncano
 */
@Service("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> op = iUsuarioRepository.findByUsername(username);
        if (op.isPresent()){
            return op.get();            
        } else {
            throw new UsernameNotFoundException("No existe el usuario "+username);
        }
    }
    
}
