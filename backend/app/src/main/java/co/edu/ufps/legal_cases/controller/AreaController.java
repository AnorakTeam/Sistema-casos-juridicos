package co.edu.ufps.legal_cases.controller;

import co.edu.ufps.legal_cases.model.Area;
import co.edu.ufps.legal_cases.service.AreaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public List<Area> listar() {
        return areaService.listar();
    }

    @GetMapping("/{id}")
    public Area obtenerPorId(@PathVariable Long id) {
        return areaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Area crear(@RequestBody Area area) {
        return areaService.crear(area);
    }

    @PutMapping("/{id}")
    public Area actualizar(@PathVariable Long id, @RequestBody Area area) {
        return areaService.actualizar(id, area);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        areaService.eliminar(id);
    }
}
