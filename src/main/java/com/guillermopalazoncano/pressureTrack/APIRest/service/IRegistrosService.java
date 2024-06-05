/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.service;

import com.guillermopalazoncano.pressureTrack.APIRest.dto.RegistroCrear;
import com.guillermopalazoncano.pressureTrack.APIRest.model.Registros;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author guillermopalazoncano
 */
public interface IRegistrosService {
    List<Registros> obtenerPorUserId(Long userId);
    Registros registrar (Long userId, RegistroCrear registro);
    Registros obtener (int id);
    boolean eliminar (int id);
    List<Registros> obtenerPorFechas(LocalDateTime startDate, LocalDateTime endDate);

}
