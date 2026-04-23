package co.edu.ufps.legal_cases.service;

import co.edu.ufps.legal_cases.dto.TemaDTO;
import co.edu.ufps.legal_cases.exception.BusinessException;
import co.edu.ufps.legal_cases.model.Area;
import co.edu.ufps.legal_cases.model.Tema;
import co.edu.ufps.legal_cases.repository.AreaRepository;
import co.edu.ufps.legal_cases.repository.TemaRepository;
import org.springframework.stereotype.Service;
import static co.edu.ufps.legal_cases.util.NormalizacionUtils.normalizarTexto;

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
                .orElseThrow(() -> new BusinessException("Tema no encontrado con id: " + id));

        return convertirADTO(tema);
    }

    public TemaDTO crear(TemaDTO temaDTO) {
        String nombre = normalizarTexto(temaDTO.getNombre());

        Area area = areaRepository.findById(temaDTO.getAreaId())
                .orElseThrow(() -> new BusinessException("Área no encontrada con id: " + temaDTO.getAreaId()));

        if (temaRepository.existsByNombreIgnoreCaseAndAreaId(nombre, area.getId())) {
            throw new BusinessException("Ya existe un tema con ese nombre en el área seleccionada");
        }

        Tema tema = new Tema();
        tema.setNombre(nombre);
        tema.setArea(area);

        Tema temaGuardado = temaRepository.save(tema);
        return convertirADTO(temaGuardado);
    }

    public TemaDTO actualizar(Long id, TemaDTO temaDTO) {

        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tema no encontrado con id: " + id));

        if (temaDTO.getNombre() == null || temaDTO.getNombre().isBlank()) {
            throw new BusinessException("El nombre del tema es obligatorio");
        }

        if (temaDTO.getAreaId() == null) {
            throw new BusinessException("El área es obligatoria");
        }

        String nuevoNombre = normalizarTexto(temaDTO.getNombre());

        Area area = areaRepository.findById(temaDTO.getAreaId())
                .orElseThrow(() -> new BusinessException("Área no encontrada con id: " + temaDTO.getAreaId()));

        //VALIDAR SI NO HAY CAMBIOS
        boolean mismoNombre = tema.getNombre().equalsIgnoreCase(nuevoNombre);
        boolean mismaArea = tema.getArea().getId().equals(area.getId());

        if (mismoNombre && mismaArea) {
            throw new BusinessException("No hay cambios para actualizar");
        }

        //VALIDAR DUPLICADO
        if (temaRepository.existsByNombreIgnoreCaseAndAreaId(nuevoNombre, area.getId())) {
            throw new BusinessException("Ya existe un tema con ese nombre en el área seleccionada");
        }

        tema.setNombre(nuevoNombre);
        tema.setArea(area);

        Tema temaActualizado = temaRepository.save(tema);
        return convertirADTO(temaActualizado);
    }

    public void eliminar(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tema no encontrado con id: " + id));

        if (tema.getTipos() != null && !tema.getTipos().isEmpty()) {
            throw new BusinessException("No se puede eliminar el tema porque tiene tipos asociados");
        }

        temaRepository.delete(tema);
    }

    private TemaDTO convertirADTO(Tema tema) {
        return new TemaDTO(
                tema.getId(),
                tema.getNombre(),
                tema.getArea().getId());
    }
}