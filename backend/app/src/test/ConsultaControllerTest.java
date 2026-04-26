package co.edu.ufps.legal_cases.business.controller;

import co.edu.ufps.legal_cases.business.dto.ConsultaBusquedaDTO;
import co.edu.ufps.legal_cases.business.dto.ConsultaDTO;
import co.edu.ufps.legal_cases.business.service.ConsultaService;
import co.edu.ufps.legal_cases.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaService consultaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    // -----------------------------------------------------------------------
    // GET /consultas — buscar
    // -----------------------------------------------------------------------

    @Test
    void buscar_sinParametro_retornaOkConLista() throws Exception {
        ConsultaBusquedaDTO dto = new ConsultaBusquedaDTO(
                1L, "Caso de prueba", LocalDate.of(2024, 1, 15),
                "Ana", "Lopez", "111222333");
        when(consultaService.buscar("")).thenReturn(List.of(dto));

        mockMvc.perform(get("/consultas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Ana"))
                .andExpect(jsonPath("$[0].cedula").value("111222333"));
    }

    @Test
    void buscar_conParametroSearch_delegaAlServicio() throws Exception {
        when(consultaService.buscar("Lopez")).thenReturn(List.of());

        mockMvc.perform(get("/consultas").param("search", "Lopez"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(consultaService).buscar("Lopez");
    }

    @Test
    void buscar_resultadoVacio_retornaListaVaciaConOk() throws Exception {
        when(consultaService.buscar(anyString())).thenReturn(List.of());

        mockMvc.perform(get("/consultas").param("search", "nada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // -----------------------------------------------------------------------
    // GET /consultas/{id}
    // -----------------------------------------------------------------------

    @Test
    void obtenerPorId_existente_retornaOkConDTO() throws Exception {
        ConsultaDTO dto = consultaDTOValido();
        dto.setId(5L);
        when(consultaService.obtenerPorId(5L)).thenReturn(dto);

        mockMvc.perform(get("/consultas/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.estado").value("Activo"));
    }

    @Test
    void obtenerPorId_noExistente_retorna404() throws Exception {
        when(consultaService.obtenerPorId(99L))
                .thenThrow(new BusinessException("Consulta no encontrada con id: 99"));

        mockMvc.perform(get("/consultas/99"))
                .andExpect(status().isNotFound());
    }

    // -----------------------------------------------------------------------
    // POST /consultas
    // -----------------------------------------------------------------------

    @Test
    void crear_dtoValido_retornaCreated() throws Exception {
        ConsultaDTO requestDTO = consultaDTOValido();
        ConsultaDTO responseDTO = consultaDTOValido();
        responseDTO.setId(10L);

        when(consultaService.crear(any(ConsultaDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void crear_fechaNula_retorna400() throws Exception {
        ConsultaDTO dto = consultaDTOValido();
        dto.setFecha(null);

        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_descripcionVacia_retorna400() throws Exception {
        ConsultaDTO dto = consultaDTOValido();
        dto.setDescripcion("");

        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_estadoNulo_retorna400() throws Exception {
        ConsultaDTO dto = consultaDTOValido();
        dto.setEstado(null);

        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // -----------------------------------------------------------------------
    // PUT /consultas/{id}
    // -----------------------------------------------------------------------

    @Test
    void actualizar_existente_retornaOkConDTOActualizado() throws Exception {
        ConsultaDTO dto = consultaDTOValido();
        ConsultaDTO updated = consultaDTOValido();
        updated.setId(3L);
        updated.setDescripcion("Descripcion actualizada");

        when(consultaService.actualizar(eq(3L), any(ConsultaDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/consultas/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Descripcion actualizada"));
    }

    @Test
    void actualizar_noExistente_retorna404() throws Exception {
        when(consultaService.actualizar(eq(99L), any(ConsultaDTO.class)))
                .thenThrow(new BusinessException("Consulta no encontrada con id: 99"));

        mockMvc.perform(put("/consultas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consultaDTOValido())))
                .andExpect(status().isNotFound());
    }

    // -----------------------------------------------------------------------
    // DELETE /consultas/{id}
    // -----------------------------------------------------------------------

    @Test
    void eliminar_existente_retornaNoContent() throws Exception {
        doNothing().when(consultaService).eliminar(7L);

        mockMvc.perform(delete("/consultas/7"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_noExistente_retorna404() throws Exception {
        doThrow(new BusinessException("Consulta no encontrada con id: 55"))
                .when(consultaService).eliminar(55L);

        mockMvc.perform(delete("/consultas/55"))
                .andExpect(status().isNotFound());
    }

    // -----------------------------------------------------------------------
    // Helper
    // -----------------------------------------------------------------------

    private ConsultaDTO consultaDTOValido() {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setFecha(LocalDate.of(2024, 5, 20));
        dto.setDescripcion("Descripcion de prueba");
        dto.setHechos("Hechos de prueba");
        dto.setPretensiones("Pretensiones de prueba");
        dto.setConceptoJuridico("Concepto juridico de prueba");
        dto.setTramite("Tramite de prueba");
        dto.setEstado("Activo");
        dto.setPersonaId(1L);
        dto.setSedeId(1L);
        dto.setAreaId(1L);
        dto.setTemaId(1L);
        return dto;
    }
}