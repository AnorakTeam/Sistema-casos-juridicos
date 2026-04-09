package co.edu.ufps.legal_cases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TemaDTO {

    private Long id;

    @NotBlank(message = "El nombre del tema es obligatorio")
    @Size(max = 80, message = "El nombre del tema no puede superar los 80 caracteres")
    private String nombre;

    @NotNull(message = "El área es obligatoria")
    private Long areaId;

    public TemaDTO() {
    }

    public TemaDTO(Long id, String nombre, Long areaId) {
        this.id = id;
        this.nombre = nombre;
        this.areaId = areaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
}
