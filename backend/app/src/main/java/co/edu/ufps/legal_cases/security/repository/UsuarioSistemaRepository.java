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

    //Aqui sobreescribe para poder cargar el rol y los permisos con el usuario
    @Override
    @EntityGraph(attributePaths = {"rol", "rol.permisos"})
    List<UsuarioSistema> findAll();

    //Tambien sobreescribe para cargar el rol y permisos, pero solo los activos
    @EntityGraph(attributePaths = {"rol", "rol.permisos"})
    List<UsuarioSistema> findByActivoTrue();

    // Para validar que un perfil no tenga 2 usuarios asociados.
    boolean existsByAsesor_Id(Long asesorId);

    boolean existsByEstudiante_Id(Long estudianteId);

    boolean existsByMonitor_Id(Long monitorId);

    boolean existsByAdministrativo_Id(Long administrativoId);

    boolean existsByConciliador_Id(Long conciliadorId);

    @EntityGraph(attributePaths = {"rol", "rol.permisos"})
    Optional<UsuarioSistema> findWithRolAndPermisosById(Long id);

    //Tambien se puede usar para cargar el rol y permisos al buscar por username
    @EntityGraph(attributePaths = {"rol", "rol.permisos"})
    Optional<UsuarioSistema> findWithRolAndPermisosByUsernameIgnoreCase(String username);
}