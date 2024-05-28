/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.repository;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author guillermopalazoncano
 */
public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);
}
