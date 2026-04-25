package co.edu.ufps.legal_cases.security.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.ufps.legal_cases.exception.BusinessException;
import co.edu.ufps.legal_cases.security.dto.UsuarioSistemaDTO;
import co.edu.ufps.legal_cases.security.model.Permiso;
import co.edu.ufps.legal_cases.security.model.UsuarioSistema;
import co.edu.ufps.legal_cases.security.repository.UsuarioSistemaRepository;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioSistemaRepository) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioSistemaDTO> listar() {
        return usuarioSistemaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioSistemaDTO> listarActivos() {
        return usuarioSistemaRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioSistemaDTO obtenerPorId(Long id) {
        UsuarioSistema usuario = buscarUsuarioConRolYPermisos(id);
        return convertirADTO(usuario);
    }

    @Transactional
    public UsuarioSistemaDTO cambiarEstado(Long id, Boolean activo) {
        UsuarioSistema usuario = buscarUsuarioConRolYPermisos(id);

        if (activo == null) {
            throw new BusinessException("El estado activo es obligatorio");
        }

        if (Objects.equals(usuario.getActivo(), activo)) {
            throw new BusinessException("El usuario ya tiene ese estado");
        }

        usuario.setActivo(activo);

        return convertirADTO(usuarioSistemaRepository.save(usuario));
    }

    private UsuarioSistema buscarUsuarioConRolYPermisos(Long id) {
        return usuarioSistemaRepository.findWithRolAndPermisosById(id)
                .orElseThrow(() -> new BusinessException("Usuario del sistema no encontrado con id: " + id));
    }

    private UsuarioSistemaDTO convertirADTO(UsuarioSistema usuario) {
        UsuarioSistemaDTO dto = new UsuarioSistemaDTO();

        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setActivo(usuario.getActivo());

        if (usuario.getRol() != null) {
            dto.setRolId(usuario.getRol().getId());
            dto.setRolNombre(usuario.getRol().getNombre());

            List<String> permisos = usuario.getRol().getPermisos()
                    .stream()
                    .filter(permiso -> Boolean.TRUE.equals(permiso.getActivo()))
                    .sorted(Comparator.comparing(Permiso::getNombre))
                    .map(Permiso::getNombre)
                    .toList();

            dto.setPermisos(permisos);
        }

        asignarPerfil(dto, usuario);

        return dto;
    }

    // Identifica a qué perfil real pertenece el usuario del sistema.
    private void asignarPerfil(UsuarioSistemaDTO dto, UsuarioSistema usuario) {
        if (usuario.getAsesor() != null) {
            dto.setPerfilId(usuario.getAsesor().getId());
            dto.setTipoPerfil("ASESOR");
            return;
        }

        if (usuario.getEstudiante() != null) {
            dto.setPerfilId(usuario.getEstudiante().getId());
            dto.setTipoPerfil("ESTUDIANTE");
            return;
        }

        if (usuario.getMonitor() != null) {
            dto.setPerfilId(usuario.getMonitor().getId());
            dto.setTipoPerfil("MONITOR");
            return;
        }

        if (usuario.getAdministrativo() != null) {
            dto.setPerfilId(usuario.getAdministrativo().getId());
            dto.setTipoPerfil("ADMINISTRATIVO");
            return;
        }

        if (usuario.getConciliador() != null) {
            dto.setPerfilId(usuario.getConciliador().getId());
            dto.setTipoPerfil("CONCILIADOR");
            return;
        }

        dto.setPerfilId(null);
        dto.setTipoPerfil("SIN_PERFIL");
    }
}