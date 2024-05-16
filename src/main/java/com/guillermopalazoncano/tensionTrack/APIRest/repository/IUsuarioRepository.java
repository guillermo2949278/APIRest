/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest.repository;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author guillermopalazoncano
 */
public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
}
