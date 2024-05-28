/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.pressureTrack.APIRest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author guillermopalazoncano
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter @Setter
public class RegistroCrear {
    int sistolica, diastolica;
}
