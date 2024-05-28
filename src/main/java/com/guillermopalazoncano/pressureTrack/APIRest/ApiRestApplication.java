package com.guillermopalazoncano.pressureTrack.APIRest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestApplication {

    
    
    public static void main(String[] args) {
        cargaVariablesEntorno();
        SpringApplication.run(ApiRestApplication.class, args);
    }
    
    private static void cargaVariablesEntorno(){
        String jwtSecret, cadenaBBDD;
        if (Utilidades.isRunningOnRender()) {
            jwtSecret = System.getenv("JWT_SECRET");
            cadenaBBDD = System.getenv("CADENABBDD");
        } else {
            Dotenv dotenv = Dotenv.load();
            jwtSecret = dotenv.get("JWT_SECRET");
            cadenaBBDD = dotenv.get("CADENABBDD");
        }
        System.setProperty("JWT_SECRET", jwtSecret);
        System.setProperty("CADENABBDD", cadenaBBDD);
    }

   
}
