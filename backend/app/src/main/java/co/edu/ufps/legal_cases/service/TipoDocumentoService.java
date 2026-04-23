package co.edu.ufps.legal_cases.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.ufps.legal_cases.dto.TipoDocumentoDTO;
import co.edu.ufps.legal_cases.exception.BusinessException;
import co.edu.ufps.legal_cases.model.TipoDocumento;
import co.edu.ufps.legal_cases.repository.TipoDocumentoRepository;

@Service
public class TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public List<TipoDocumentoDTO> listar() {
        return tipoDocumentoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<TipoDocumentoDTO> listarActivos() {
        return tipoDocumentoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoDocumentoDTO obtenerPorId(String id) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(normalizarId(id))
                .orElseThrow(() -> new BusinessException("Tipo de documento no encontrado con id: " + id));

        return convertirADTO(tipoDocumento);
    }

    public TipoDocumentoDTO crear(TipoDocumentoDTO dto) {
        String id = normalizarId(dto.getId());
        String displayName = normalizarTexto(dto.getDisplayName());

        if (id == null || id.isBlank()) {
            throw new BusinessException("El id del tipo de documento es obligatorio");
        }

        if (displayName == null || displayName.isBlank()) {
            throw new BusinessException("El nombre visible es obligatorio");
        }

        if (tipoDocumentoRepository.existsById(id)) {
            throw new BusinessException("Ya existe un tipo de documento con ese id");
        }

        if (tipoDocumentoRepository.existsByDisplayNameIgnoreCase(displayName)) {
            throw new BusinessException("Ya existe un tipo de documento con ese nombre");
        }

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setId(id);
        tipoDocumento.setDisplayName(displayName);
        tipoDocumento.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return convertirADTO(tipoDocumentoRepository.save(tipoDocumento));
    }

    public TipoDocumentoDTO actualizar(String id, TipoDocumentoDTO dto) {
        String idNormalizado = normalizarId(id);

        TipoDocumento documentoExistente = tipoDocumentoRepository.findById(idNormalizado)
                .orElseThrow(() -> new BusinessException("Tipo de documento no encontrado con id: " + id));

        String displayNameNuevo = normalizarTexto(dto.getDisplayName());

        if (displayNameNuevo == null || displayNameNuevo.isBlank()) {
            throw new BusinessException("El nombre visible es obligatorio");
        }

        boolean mismoNombre = documentoExistente.getDisplayName().equalsIgnoreCase(displayNameNuevo);
        boolean mismoEstado = dto.getActivo() == null || documentoExistente.getActivo().equals(dto.getActivo());

        if (mismoNombre && mismoEstado) {
            throw new BusinessException("No hay cambios para actualizar");
        }

        if (tipoDocumentoRepository.existsByDisplayNameIgnoreCaseAndIdNot(displayNameNuevo, documentoExistente.getId())) {
            throw new BusinessException("Ya existe un tipo de documento con ese nombre");
        }

        documentoExistente.setDisplayName(displayNameNuevo);

        if (dto.getActivo() != null) {
            documentoExistente.setActivo(dto.getActivo());
        }

        return convertirADTO(tipoDocumentoRepository.save(documentoExistente));
    }

    public TipoDocumentoDTO cambiarEstado(String id, Boolean activo) {
        TipoDocumento documentoExistente = tipoDocumentoRepository.findById(normalizarId(id))
                .orElseThrow(() -> new BusinessException("Tipo de documento no encontrado con id: " + id));

        if (activo == null) {
            throw new BusinessException("El estado activo es obligatorio");
        }

        if (documentoExistente.getActivo().equals(activo)) {
            throw new BusinessException("El tipo de documento ya tiene ese estado");
        }

        documentoExistente.setActivo(activo);
        return convertirADTO(tipoDocumentoRepository.save(documentoExistente));
    }

    private TipoDocumentoDTO convertirADTO(TipoDocumento tipoDocumento) {
        TipoDocumentoDTO dto = new TipoDocumentoDTO();
        dto.setId(tipoDocumento.getId());
        dto.setDisplayName(tipoDocumento.getDisplayName());
        dto.setActivo(tipoDocumento.getActivo());
        return dto;
    }

    private String normalizarId(String id) {
        if (id == null) {
            return null;
        }
        return id.trim().toUpperCase();
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return null;
        }

        String limpio = texto.trim();

        if (limpio.isBlank()) {
            return null;
        }
        // Reemplaza múltiples espacios por uno solo
        return limpio.replaceAll("\\s+", " ");
    }
}