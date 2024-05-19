package com.guillermopalazoncano.tensionTrack.APIRest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestApplication {

    public static void main(String[] args) {
        String jwtSecret;
        if (Utilidades.isRunningOnRender()) {
            jwtSecret = System.getenv("JWT_SECRET");
        } else {
            Dotenv dotenv = Dotenv.load();
            jwtSecret = dotenv.get("JWT_SECRET");
        }
        System.setProperty("JWT_SECRET", jwtSecret);
        SpringApplication.run(ApiRestApplication.class, args);
    }

    /*
    @PostConstruct
    private void postConstruct(){
        Usuario usu = new Usuario("carre@carre.com","carre");
        usuService.registrar(usu);
    }
     */
}
