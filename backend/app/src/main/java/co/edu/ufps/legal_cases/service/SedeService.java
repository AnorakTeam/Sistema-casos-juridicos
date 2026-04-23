package co.edu.ufps.legal_cases.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.ufps.legal_cases.dto.SedeDTO;
import co.edu.ufps.legal_cases.exception.BusinessException;
import co.edu.ufps.legal_cases.model.Sede;
import co.edu.ufps.legal_cases.repository.SedeRepository;

import static co.edu.ufps.legal_cases.util.NormalizacionUtils.normalizarId;
import static co.edu.ufps.legal_cases.util.NormalizacionUtils.normalizarTexto;

@Service
public class SedeService {

    private final SedeRepository sedeRepository;

    public SedeService(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }

    public List<SedeDTO> listar() {
        return sedeRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public SedeDTO obtenerPorId(String id) {
        Sede sede = sedeRepository.findById(normalizarId(id))
                .orElseThrow(() -> new BusinessException("Sede no encontrada con id: " + id));

        return convertirADTO(sede);
    }

    public SedeDTO crear(SedeDTO dto) {
        String id = normalizarId(dto.getId());
        String nombre = normalizarTexto(dto.getNombre());

        if (id == null || id.isBlank()) {
            throw new BusinessException("El id de la sede es obligatorio");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new BusinessException("El nombre de la sede es obligatorio");
        }

        if (sedeRepository.existsById(id)) {
            throw new BusinessException("Ya existe una sede con ese id");
        }

        if (sedeRepository.existsByNombreIgnoreCase(nombre)) {
            throw new BusinessException("Ya existe una sede con ese nombre");
        }

        Sede sede = new Sede();
        sede.setId(id);
        sede.setNombre(nombre);

        return convertirADTO(sedeRepository.save(sede));
    }

    public SedeDTO actualizar(String id, SedeDTO dto) {
        String idNormalizado = normalizarId(id);

        Sede sedeExistente = sedeRepository.findById(idNormalizado)
                .orElseThrow(() -> new BusinessException("Sede no encontrada con id: " + id));

        String nombreNuevo = normalizarTexto(dto.getNombre());

        if (nombreNuevo == null || nombreNuevo.isBlank()) {
            throw new BusinessException("El nombre de la sede es obligatorio");
        }

        // El id de un catálogo no debería cambiarse una vez creado
        if (dto.getId() != null && !normalizarId(dto.getId()).equals(sedeExistente.getId())) {
            throw new BusinessException("No se permite cambiar el id de la sede");
        }

        boolean mismoNombre = sedeExistente.getNombre().equalsIgnoreCase(nombreNuevo);

        if (mismoNombre) {
            throw new BusinessException("No hay cambios para actualizar");
        }

        if (sedeRepository.existsByNombreIgnoreCaseAndIdNot(nombreNuevo, sedeExistente.getId())) {
            throw new BusinessException("Ya existe una sede con ese nombre");
        }

        sedeExistente.setNombre(nombreNuevo);

        return convertirADTO(sedeRepository.save(sedeExistente));
    }

    public void eliminar(String id) {
        Sede sede = sedeRepository.findById(normalizarId(id))
                .orElseThrow(() -> new BusinessException("Sede no encontrada con id: " + id));

        // Aquí hay que validar referencias antes de eliminar:
        // asesor, estudiante, monitor, administrativo, consulta, conciliador, etc.
        sedeRepository.delete(sede);
    }

    private SedeDTO convertirADTO(Sede sede) {
        SedeDTO dto = new SedeDTO();
        dto.setId(sede.getId());
        dto.setNombre(sede.getNombre());
        return dto;
    }
}