package co.edu.ufps.legal_cases.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.legal_cases.security.model.UsuarioSistema;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {

    Optional<UsuarioSistema> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    List<UsuarioSistema> findByActivoTrue();

    //Para validar que un perfil no tenga 2 usuarios asociados
    boolean existsByAsesor_Id(Long asesorId);

    boolean existsByEstudiante_Id(Long estudianteId);

    boolean existsByMonitor_Id(Long monitorId);

    boolean existsByAdministrativo_Id(Long administrativoId);

    boolean existsByConciliador_Id(Long conciliadorId);

    // Permite cargar el usuario junto con su rol y permisos para evitar problemas de LazyInitializationException
    @EntityGraph(attributePaths = {"rol", "rol.permisos"})
    Optional<UsuarioSistema> findWithRolAndPermisosByUsernameIgnoreCase(String username);
}