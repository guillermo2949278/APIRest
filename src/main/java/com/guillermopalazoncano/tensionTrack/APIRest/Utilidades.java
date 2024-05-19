/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.guillermopalazoncano.tensionTrack.APIRest;

/**
 *
 * @author guillermopalazoncano
 */
public class Utilidades {
    public static boolean isRunningOnRender() {
        String renderEnv = System.getenv("RENDER");
        return renderEnv != null && renderEnv.equalsIgnoreCase("true");
    }
}
