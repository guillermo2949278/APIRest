package com.guillermopalazoncano.tensionTrack.APIRest;

import com.guillermopalazoncano.tensionTrack.APIRest.model.Usuario;
import com.guillermopalazoncano.tensionTrack.APIRest.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestApplication {

    @Autowired
    UsuarioService usuService;
    
    public static void main(String[] args)  {
        SpringApplication.run(ApiRestApplication.class, args);
    }

    @PostConstruct
    private void postConstruct(){
        Usuario usu = new Usuario("carre@carre.com","carre");
        usuService.registrar(usu);
    }
    
    
     
        

}
