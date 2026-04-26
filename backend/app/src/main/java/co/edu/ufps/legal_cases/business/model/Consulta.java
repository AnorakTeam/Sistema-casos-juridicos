package co.edu.ufps.legal_cases.business.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consulta")
@Getter
@Setter
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "hechos", nullable = false, columnDefinition = "TEXT")
    private String hechos;

    @Column(name = "pretensiones", nullable = false, columnDefinition = "TEXT")
    private String pretensiones;

    @Column(name = "concepto_juridico", nullable = false, columnDefinition = "TEXT")
    private String conceptoJuridico;

    @Column(name = "tramite", nullable = false, length = 100)
    private String tramite;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "tipo_violencia", length = 100)
    private String tipoViolencia;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "resultado", length = 100)
    private String resultado;

    // Relaciones con usuarios del sistema
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id", nullable = false)
    private Tema tema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asesor_id")
    private Asesor asesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    private Monitor monitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
}