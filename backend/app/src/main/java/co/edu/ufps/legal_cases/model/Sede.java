package co.edu.ufps.legal_cases.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sede")
@Getter
@Setter
public class Sede {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    //Aqui hay que añadir nuevas anotaciones para listar asociados por sede, como asesor, estudiante, monitor, administrativo, consulta, conciliador, etc.
}