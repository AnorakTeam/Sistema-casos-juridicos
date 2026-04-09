package co.edu.ufps.legal_cases.service;

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

    public List<Area> listar() {
        return areaRepository.findAll();
    }

    public Area obtenerPorId(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));
    }

    public Area crear(Area area) {
        if (areaRepository.existsByNombre(area.getNombre())) {
            throw new RuntimeException("Ya existe un área con ese nombre");
        }
        return areaRepository.save(area);
    }

    public Area actualizar(Long id, Area areaActualizada) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));
        
        //verifica que el nuevo nombre no coincida con el de otra area
        if (!area.getNombre().equalsIgnoreCase(areaActualizada.getNombre())
                && areaRepository.existsByNombre(areaActualizada.getNombre())) {
            throw new RuntimeException("Ya existe un área con ese nombre");
        }

        area.setNombre(areaActualizada.getNombre());
        return areaRepository.save(area);
    }

    public void eliminar(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + id));

        areaRepository.delete(area);
    }
}
