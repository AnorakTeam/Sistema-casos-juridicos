package co.edu.ufps.legal_cases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TipoDTO {

    private Long id;

    @NotBlank(message = "El nombre del tipo es obligatorio")
    @Size(max = 80, message = "El nombre del tipo no puede superar los 80 caracteres")
    private String nombre;

    @NotNull(message = "El tema es obligatorio")
    private Long temaId;

    public TipoDTO() {
    }

    public TipoDTO(Long id, String nombre, Long temaId) {
        this.id = id;
        this.nombre = nombre;
        this.temaId = temaId;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getTemaId() {
        return temaId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }
}