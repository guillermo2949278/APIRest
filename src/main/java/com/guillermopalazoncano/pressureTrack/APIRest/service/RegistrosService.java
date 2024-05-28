/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.service;

import com.guillermopalazoncano.pressureTrack.APIRest.dto.RegistroCrear;
import com.guillermopalazoncano.pressureTrack.APIRest.model.Registros;
import com.guillermopalazoncano.pressureTrack.APIRest.repository.IRegistrosRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author guillermopalazoncano
 */
@Service
public class RegistrosService implements IRegistrosService {

    @Autowired
    private IRegistrosRepository iRegistrosRepository;
    
    @Override
    public List<Registros> obtenerPorUserId(Long userId) {
        return iRegistrosRepository.findByUserId(userId);
    }

    @Override
    public Registros registrar(Long userId, RegistroCrear reg) {
        Registros registro = new Registros(userId, LocalDateTime.now(), reg.getSistolica(), reg.getDiastolica());
        return iRegistrosRepository.save(registro);            
    }

    @Override
    public Registros obtener(int id) {
        long numero = (long) id;
        Optional<Registros> op = iRegistrosRepository.findById(numero);
        return op.isPresent() ? op.get() : null;
    }
    
    @Override
    public boolean eliminar(int id) {
        long numero = (long) id;
        if (iRegistrosRepository.existsById(numero)){
            iRegistrosRepository.deleteById(numero);
            return true;
        } else {
            return false;
        }
    }
}
