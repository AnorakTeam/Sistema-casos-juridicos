package co.edu.ufps.legal_cases.security.model;

import co.edu.ufps.legal_cases.business.model.Administrativo;
import co.edu.ufps.legal_cases.business.model.Asesor;
import co.edu.ufps.legal_cases.business.model.Conciliador;
import co.edu.ufps.legal_cases.business.model.Estudiante;
import co.edu.ufps.legal_cases.business.model.Monitor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario_sistema")
@Getter
@Setter
public class UsuarioSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se usa el correo como usuario de acceso al sistema.
    @Column(name = "username", nullable = false, unique = true, length = 120)
    private String username;

    // La contraseña no se guarda en texto plano, se guarda cifrada.
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // Rol administrable asignado al usuario.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    // Perfiles reales del sistema. Solo uno debe estar informado por usuario.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asesor_id", unique = true)
    private Asesor asesor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", unique = true)
    private Estudiante estudiante;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", unique = true)
    private Monitor monitor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrativo_id", unique = true)
    private Administrativo administrativo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conciliador_id", unique = true)
    private Conciliador conciliador;
}