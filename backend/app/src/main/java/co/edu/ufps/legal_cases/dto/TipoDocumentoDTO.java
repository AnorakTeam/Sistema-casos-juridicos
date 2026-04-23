package co.edu.ufps.legal_cases.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoDocumentoDTO {

    @NotBlank(message = "El id del tipo de documento es obligatorio")
    @Size(max = 20, message = "El id no puede superar los 20 caracteres")
    @Pattern(
        regexp = "^[A-Za-z0-9_\\-]+$",
        message = "El id solo puede contener letras, números, guion o guion bajo"
    )
    private String id;

    @NotBlank(message = "El nombre visible es obligatorio")
    @Size(max = 100, message = "El nombre visible no puede superar los 100 caracteres")
    private String displayName;

    private Boolean activo;
}