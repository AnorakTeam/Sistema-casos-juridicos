package co.edu.ufps.legal_cases.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.ufps.legal_cases.dto.SedeDTO;
import co.edu.ufps.legal_cases.service.SedeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sedes")
@CrossOrigin(origins = "*")
public class SedeController {

    private final SedeService sedeService;

    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    @GetMapping
    public List<SedeDTO> listar() {
        return sedeService.listar();
    }

    @GetMapping("/{id}")
    public SedeDTO obtenerPorId(@PathVariable String id) {
        return sedeService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SedeDTO crear(@Valid @RequestBody SedeDTO dto) {
        return sedeService.crear(dto);
    }

    @PutMapping("/{id}")
    public SedeDTO actualizar(@PathVariable String id, @Valid @RequestBody SedeDTO dto) {
        return sedeService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id) {
        sedeService.eliminar(id);
    }
}
