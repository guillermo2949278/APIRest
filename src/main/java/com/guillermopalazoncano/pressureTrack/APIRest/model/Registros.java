/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
/**
 *
 * @author guillermopalazoncano
 */
@Entity
@Table(name="registros")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter @Setter
public class Registros{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="registro_id")
    private Long registroId;

    @Column(name="user_id")
    private Long userId;
    
    @Column(name="fecha_hora")
    private LocalDateTime fechaHora;
    
    @Column(name="sistolica")
    private int sistolica;

    @Column(name="diastolica")
    private int diastolica;

    public Registros(Long userId, LocalDateTime fechaHora, int sistolica, int diastolica) {
        this.userId = userId;
        this.fechaHora = fechaHora;
        this.sistolica = sistolica;
        this.diastolica = diastolica;
    }
    
    
    
}
