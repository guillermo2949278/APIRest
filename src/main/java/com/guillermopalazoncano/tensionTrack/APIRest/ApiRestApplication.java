package com.guillermopalazoncano.tensionTrack.APIRest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestApplication{

    public static void main(String[] args)  {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        //System.out.println("jwt_secret: "+dotenv.get("JWT_SECRET"));
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
