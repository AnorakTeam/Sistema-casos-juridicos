package co.edu.ufps.legal_cases.service;

import co.edu.ufps.legal_cases.dto.TemaDTO;
import co.edu.ufps.legal_cases.model.Area;
import co.edu.ufps.legal_cases.model.Tema;
import co.edu.ufps.legal_cases.repository.AreaRepository;
import co.edu.ufps.legal_cases.repository.TemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemaService {

    private final TemaRepository temaRepository;
    private final AreaRepository areaRepository;

    public TemaService(TemaRepository temaRepository, AreaRepository areaRepository) {
        this.temaRepository = temaRepository;
        this.areaRepository = areaRepository;
    }

    public List<TemaDTO> listar() {
        return temaRepository.findAll()
                .stream()
                .map(tema -> convertirADTO(tema))
                .toList();
    }

    public List<TemaDTO> listarPorArea(Long areaId) {
        return temaRepository.findByAreaId(areaId)
                .stream()
                .map(tema -> convertirADTO(tema))
                .toList();
    }

    public TemaDTO obtenerPorId(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + id));

        return convertirADTO(tema);
    }

    public TemaDTO crear(TemaDTO temaDTO) {
        String nombre = temaDTO.getNombre().trim();

        Area area = areaRepository.findById(temaDTO.getAreaId())
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + temaDTO.getAreaId()));

        if (temaRepository.existsByNombreAndAreaId(nombre, area.getId())) {
            throw new RuntimeException("Ya existe un tema con ese nombre en el área seleccionada");
        }

        Tema tema = new Tema();
        tema.setNombre(nombre);
        tema.setArea(area);

        Tema temaGuardado = temaRepository.save(tema);
        return convertirADTO(temaGuardado);
    }

    public TemaDTO actualizar(Long id, TemaDTO temaDTO) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + id));

        String nuevoNombre = temaDTO.getNombre().trim();

        Area area = areaRepository.findById(temaDTO.getAreaId())
                .orElseThrow(() -> new RuntimeException("Área no encontrada con id: " + temaDTO.getAreaId()));

        if ((!tema.getNombre().equalsIgnoreCase(nuevoNombre) || !tema.getArea().getId().equals(area.getId()))
                && temaRepository.existsByNombreAndAreaId(nuevoNombre, area.getId())) {
            throw new RuntimeException("Ya existe un tema con ese nombre en el área seleccionada");
        }

        tema.setNombre(nuevoNombre);
        tema.setArea(area);

        Tema temaActualizado = temaRepository.save(tema);
        return convertirADTO(temaActualizado);
    }

    public void eliminar(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + id));

        if (tema.getTipos() != null && !tema.getTipos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el tema porque tiene tipos asociados");
        }

        temaRepository.delete(tema);
    }

    private TemaDTO convertirADTO(Tema tema) {
        return new TemaDTO(
                tema.getId(),
                tema.getNombre(),
                tema.getArea().getId()
        );
    }
}