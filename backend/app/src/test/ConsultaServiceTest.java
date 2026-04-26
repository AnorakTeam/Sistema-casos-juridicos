package co.edu.ufps.legal_cases.business.service;

import co.edu.ufps.legal_cases.business.dto.ConsultaBusquedaDTO;
import co.edu.ufps.legal_cases.business.dto.ConsultaDTO;
import co.edu.ufps.legal_cases.business.model.*;
import co.edu.ufps.legal_cases.business.repository.*;
import co.edu.ufps.legal_cases.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock private ConsultaRepository consultaRepository;
    @Mock private PersonaRepository personaRepository;
    @Mock private SedeRepository sedeRepository;
    @Mock private AreaRepository areaRepository;
    @Mock private TemaRepository temaRepository;
    @Mock private TipoRepository tipoRepository;
    @Mock private AsesorRepository asesorRepository;
    @Mock private MonitorRepository monitorRepository;
    @Mock private EstudianteRepository estudianteRepository;

    @InjectMocks
    private ConsultaService consultaService;

    // Entidades de apoyo
    private Persona persona;
    private Sede sede;
    private Area area;
    private Tema tema;

    @BeforeEach
    void setUp() {
        persona = new Persona();
        persona.setId(1L);
        persona.setNombres("Ana");
        persona.setApellidos("López");
        persona.setNumeroDocumento("987654321");

        sede = new Sede();
        sede.setId(1L);
        sede.setNombre("Sede Central");

        area = new Area();
        area.setId(1L);
        area.setNombre("Familia");

        tema = new Tema();
        tema.setId(1L);
        tema.setNombre("Custodia");
    }

    // -----------------------------------------------------------------------
    // buscar()
    // -----------------------------------------------------------------------

    @Test
    void buscar_retornaListaDeConsultaBusquedaDTO() {
        Consulta c = consultaBase();
        when(consultaRepository.buscar(anyString())).thenReturn(List.of(c));

        List<ConsultaBusquedaDTO> resultado = consultaService.buscar("Ana");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Ana");
        assertThat(resultado.get(0).getApellido()).isEqualTo("López");
        assertThat(resultado.get(0).getCedula()).isEqualTo("987654321");
    }

    @Test
    void buscar_searchNulo_delegaAlRepositorio() {
        when(consultaRepository.buscar(null)).thenReturn(List.of());

        List<ConsultaBusquedaDTO> resultado = consultaService.buscar(null);

        assertThat(resultado).isEmpty();
        verify(consultaRepository).buscar(null);
    }

    // -----------------------------------------------------------------------
    // listar()
    // -----------------------------------------------------------------------

    @Test
    void listar_retornaTodasLasConsultas() {
        when(consultaRepository.findAll()).thenReturn(List.of(consultaBase(), consultaBase()));

        List<ConsultaDTO> resultado = consultaService.listar();

        assertThat(resultado).hasSize(2);
    }

    // -----------------------------------------------------------------------
    // obtenerPorId()
    // -----------------------------------------------------------------------

    @Test
    void obtenerPorId_existente_retornaDTO() {
        Consulta c = consultaBase();
        c.setId(10L);
        when(consultaRepository.findById(10L)).thenReturn(Optional.of(c));

        ConsultaDTO dto = consultaService.obtenerPorId(10L);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getDescripcion()).isEqualTo("Descripcion test");
    }

    @Test
    void obtenerPorId_noExistente_lanzaBusinessException() {
        when(consultaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultaService.obtenerPorId(99L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("99");
    }

    // -----------------------------------------------------------------------
    // crear()
    // -----------------------------------------------------------------------

    @Test
    void crear_dtoValido_guardaYRetornaDTO() {
        ConsultaDTO dto = dtoValido();
        stubLookups();

        Consulta guardada = consultaBase();
        guardada.setId(5L);
        when(consultaRepository.save(any(Consulta.class))).thenReturn(guardada);

        ConsultaDTO resultado = consultaService.crear(dto);

        assertThat(resultado.getId()).isEqualTo(5L);
        verify(consultaRepository).save(any(Consulta.class));
    }

    @Test
    void crear_conIdEnDTO_lanzaBusinessException() {
        ConsultaDTO dto = dtoValido();
        dto.setId(1L); // no debe enviarse en creación

        assertThatThrownBy(() -> consultaService.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("id");
    }

    @Test
    void crear_fechaNula_lanzaBusinessException() {
        ConsultaDTO dto = dtoValido();
        dto.setFecha(null);

        assertThatThrownBy(() -> consultaService.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("fecha");
    }

    @Test
    void crear_descripcionVacia_lanzaBusinessException() {
        ConsultaDTO dto = dtoValido();
        dto.setDescripcion("   ");

        assertThatThrownBy(() -> consultaService.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("descripción");
    }

    @Test
    void crear_personaIdNulo_lanzaBusinessException() {
        ConsultaDTO dto = dtoValido();
        dto.setPersonaId(null);

        assertThatThrownBy(() -> consultaService.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("persona");
    }

    @Test
    void crear_personaNoEncontrada_lanzaBusinessException() {
        ConsultaDTO dto = dtoValido();
        when(personaRepository.findById(dto.getPersonaId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultaService.crear(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Persona");
    }

    // -----------------------------------------------------------------------
    // actualizar()
    // -----------------------------------------------------------------------

    @Test
    void actualizar_existente_retornaDTOActualizado() {
        Consulta existente = consultaBase();
        existente.setId(3L);
        when(consultaRepository.findById(3L)).thenReturn(Optional.of(existente));
        stubLookups();

        ConsultaDTO dto = dtoValido();
        dto.setDescripcion("Descripcion actualizada");

        Consulta actualizada = consultaBase();
        actualizada.setId(3L);
        actualizada.setDescripcion("Descripcion actualizada");
        when(consultaRepository.save(any(Consulta.class))).thenReturn(actualizada);

        ConsultaDTO resultado = consultaService.actualizar(3L, dto);

        assertThat(resultado.getDescripcion()).isEqualTo("Descripcion actualizada");
    }

    @Test
    void actualizar_noExistente_lanzaBusinessException() {
        when(consultaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultaService.actualizar(99L, dtoValido()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("99");
    }

    @Test
    void actualizar_idDistintoEnDTO_lanzaBusinessException() {
        Consulta existente = consultaBase();
        existente.setId(3L);
        when(consultaRepository.findById(3L)).thenReturn(Optional.of(existente));

        ConsultaDTO dto = dtoValido();
        dto.setId(999L); // id diferente → error

        assertThatThrownBy(() -> consultaService.actualizar(3L, dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("id");
    }

    // -----------------------------------------------------------------------
    // eliminar()
    // -----------------------------------------------------------------------

    @Test
    void eliminar_existente_eliminaCorrectamente() {
        Consulta c = consultaBase();
        c.setId(7L);
        when(consultaRepository.findById(7L)).thenReturn(Optional.of(c));

        consultaService.eliminar(7L);

        verify(consultaRepository).delete(c);
    }

    @Test
    void eliminar_noExistente_lanzaBusinessException() {
        when(consultaRepository.findById(55L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultaService.eliminar(55L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("55");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private Consulta consultaBase() {
        Consulta c = new Consulta();
        c.setFecha(LocalDate.of(2024, 3, 10));
        c.setDescripcion("Descripcion test");
        c.setHechos("Hechos test");
        c.setPretensiones("Pretensiones test");
        c.setConceptoJuridico("Concepto test");
        c.setTramite("Tramite test");
        c.setEstado("Activo");
        c.setPersona(persona);
        c.setSede(sede);
        c.setArea(area);
        c.setTema(tema);
        return c;
    }

    private ConsultaDTO dtoValido() {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setFecha(LocalDate.of(2024, 3, 10));
        dto.setDescripcion("Descripcion test");
        dto.setHechos("Hechos test");
        dto.setPretensiones("Pretensiones test");
        dto.setConceptoJuridico("Concepto test");
        dto.setTramite("Tramite test");
        dto.setEstado("Activo");
        dto.setPersonaId(1L);
        dto.setSedeId(1L);
        dto.setAreaId(1L);
        dto.setTemaId(1L);
        return dto;
    }

    private void stubLookups() {
        when(personaRepository.findById(1L)).thenReturn(Optional.of(persona));
        when(sedeRepository.findById(1L)).thenReturn(Optional.of(sede));
        when(areaRepository.findById(1L)).thenReturn(Optional.of(area));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
    }
}
