package co.edu.ufps.legal_cases.security.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    //Despues de autenticar es lo que le devuelve al frontend
    private Long usuarioId;

    private String username;

    private Long rolId;

    private String rolNombre;

    private Long perfilId;

    private String tipoPerfil;

    private List<String> permisos;
}