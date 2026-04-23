package co.edu.ufps.legal_cases.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.ufps.legal_cases.dto.TipoDocumentoDTO;
import co.edu.ufps.legal_cases.service.TipoDocumentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tipos-documento")
//Modificar despues
@CrossOrigin(origins = "*")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @GetMapping
    public List<TipoDocumentoDTO> listar() {
        return tipoDocumentoService.listar();
    }

    @GetMapping("/activos")
    public List<TipoDocumentoDTO> listarActivos() {
        return tipoDocumentoService.listarActivos();
    }

    @GetMapping("/{id}")
    public TipoDocumentoDTO obtenerPorId(@PathVariable Long id) {
        return tipoDocumentoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoDocumentoDTO crear(@Valid @RequestBody TipoDocumentoDTO dto) {
        return tipoDocumentoService.crear(dto);
    }

    @PutMapping("/{id}")
    public TipoDocumentoDTO actualizar(@PathVariable Long id, @Valid @RequestBody TipoDocumentoDTO dto) {
        return tipoDocumentoService.actualizar(id, dto);
    }

    @PatchMapping("/{id}/activo")
    public TipoDocumentoDTO cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        return tipoDocumentoService.cambiarEstado(id, activo);
    }
}