package co.edu.ufps.legal_cases.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipodoc")
@Getter
@Setter
public class TipoDocumento {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "displayname", nullable = false, length = 100)
    private String displayName;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}