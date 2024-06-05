/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.repository;

import com.guillermopalazoncano.pressureTrack.APIRest.model.Registros;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author guillermopalazoncano
 */
public interface IRegistrosRepository extends JpaRepository<Registros,Long>  {
    @Query("SELECT r FROM Registros r WHERE r.userId = :userId")
    List<Registros> findByUserId(@Param("userId") long userId);
    
    @Query("SELECT r FROM Registros r WHERE r.fechaHora BETWEEN :startDate AND :endDate")
    List<Registros> findRegistrosPorFechas(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
