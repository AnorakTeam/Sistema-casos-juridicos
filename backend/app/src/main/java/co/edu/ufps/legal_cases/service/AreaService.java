package co.edu.ufps.legal_cases.service;

import co.edu.ufps.legal_cases.dto.AreaDTO;
import co.edu.ufps.legal_cases.model.Area;
import co.edu.ufps.legal_cases.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaDTO> listar() {
        // Obtiene todas las áreas y las convierte a DTO
        return areaRepository.findAll()
                .stream()
                .map(area -> convertirADTO(area))
                .toList();
    }

    public AreaDTO obtenerPorId(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));

        return convertirADTO(area);
    }

    public AreaDTO crear(AreaDTO areaDTO) {
        String nombre = normalizarNombre(areaDTO.getNombre());

        if (nombre == null || nombre.isBlank()) {
            throw new RuntimeException("El nombre del área es obligatorio");
        }

        if (areaRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un área con ese nombre");
        }

        Area area = new Area();
        area.setNombre(nombre);

        Area areaGuardada = areaRepository.save(area);
        return convertirADTO(areaGuardada);
    }

    public AreaDTO actualizar(Long id, AreaDTO areaDTO) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));

        String nuevoNombre = normalizarNombre(areaDTO.getNombre());

        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new RuntimeException("El nombre del área es obligatorio");
        }

        if (!area.getNombre().equalsIgnoreCase(nuevoNombre)
                && areaRepository.existsByNombre(nuevoNombre)) {
            throw new RuntimeException("Ya existe un área con ese nombre");
        }

        area.setNombre(nuevoNombre);
        Area areaActualizada = areaRepository.save(area);

        return convertirADTO(areaActualizada);
    }

    public void eliminar(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));

        //Para evitar eliminar temas accidentalmente en cascada 
        if (area.getTemas() != null && !area.getTemas().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el área porque tiene temas asociados");
        }

        areaRepository.delete(area);
    }

    private AreaDTO convertirADTO(Area area) {
        return new AreaDTO(area.getId(), area.getNombre());
    }

    private String normalizarNombre(String nombre) {
        if (nombre == null) {
            return null;
        }
        //eliminar espacios vacios a los extremos
        return nombre.trim();
    }
}