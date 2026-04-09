package co.edu.ufps.legal_cases.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/* TODO: obviamente eliminar esta clase después, sólo un placeholder para que en el 
repo se creen los directorios/paquetes */

@RestController
public class TestController {

    @GetMapping("/hello")
    public MessageResponse hello() {
        return new MessageResponse("Hello World");
    }

    record MessageResponse(String message) {}
}
