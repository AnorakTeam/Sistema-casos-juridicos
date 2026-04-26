package co.edu.ufps.legal_cases.business.repository;

import co.edu.ufps.legal_cases.business.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConsultaRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ConsultaRepository consultaRepository;

    private Persona persona;
    private Sede sede;
    private Area area;
    private Tema tema;

    @BeforeEach
    void setUp() {
        // Persona mínima requerida por la relación
        persona = new Persona();
        persona.setNombres("Carlos");
        persona.setApellidos("Perez");
        persona.setNumeroDocumento("123456789");
        em.persist(persona);

        sede = new Sede();
        sede.setNombre("Sede Norte");
        em.persist(sede);

        area = new Area();
        area.setNombre("Derecho Civil");
        em.persist(area);

        tema = new Tema();
        tema.setNombre("Familia");
        em.persist(tema);

        em.flush();
    }

    private Consulta crearConsulta(String descripcion) {
        Consulta c = new Consulta();
        c.setFecha(LocalDate.now());
        c.setDescripcion(descripcion);
        c.setHechos("Hechos de prueba");
        c.setPretensiones("Pretensiones de prueba");
        c.setConceptoJuridico("Concepto de prueba");
        c.setTramite("Tramite");
        c.setEstado("Activo");
        c.setPersona(persona);
        c.setSede(sede);
        c.setArea(area);
        c.setTema(tema);
        return c;
    }

    // -----------------------------------------------------------------------
    // buscar() — retorna todas cuando search es null o vacío
    // -----------------------------------------------------------------------

    @Test
    void buscar_sinFiltro_retornaTodasLasConsultas() {
        em.persist(crearConsulta("Consulta de divorcio"));
        em.persist(crearConsulta("Consulta de herencia"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar(null);

        assertThat(resultado).hasSize(2);
    }

    @Test
    void buscar_stringVacio_retornaTodasLasConsultas() {
        em.persist(crearConsulta("Primer caso"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar("");

        assertThat(resultado).hasSize(1);
    }

    // -----------------------------------------------------------------------
    // buscar() — filtro por descripción
    // -----------------------------------------------------------------------

    @Test
    void buscar_porDescripcion_retornaCoincidencias() {
        em.persist(crearConsulta("Caso de violencia familiar"));
        em.persist(crearConsulta("Consulta laboral"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar("violencia");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescripcion()).containsIgnoringCase("violencia");
    }

    @Test
    void buscar_porDescripcionInsensibleMayusculas_retornaCoincidencia() {
        em.persist(crearConsulta("Caso de Herencia"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar("HERENCIA");

        assertThat(resultado).hasSize(1);
    }

    // -----------------------------------------------------------------------
    // buscar() — filtro por datos de persona
    // -----------------------------------------------------------------------

    @Test
    void buscar_porNombrePersona_retornaConsultasDeEsaPersona() {
        em.persist(crearConsulta("Descripcion cualquiera"));
        em.flush();

        // La persona del setUp se llama "Carlos"
        List<Consulta> resultado = consultaRepository.buscar("Carlos");

        assertThat(resultado).hasSize(1);
    }

    @Test
    void buscar_porApellidoPersona_retornaConsultasDeEsaPersona() {
        em.persist(crearConsulta("Descripcion cualquiera"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar("Perez");

        assertThat(resultado).hasSize(1);
    }

    @Test
    void buscar_porNumeroDocumento_retornaConsultasDeEsaPersona() {
        em.persist(crearConsulta("Descripcion cualquiera"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar("123456789");

        assertThat(resultado).hasSize(1);
    }

    // -----------------------------------------------------------------------
    // buscar() — sin coincidencias
    // -----------------------------------------------------------------------

    @Test
    void buscar_terminoSinCoincidencias_retornaListaVacia() {
        em.persist(crearConsulta("Consulta de divorcio"));
        em.flush();

        List<Consulta> resultado = consultaRepository.buscar("xyzterminoquenoexiste");

        assertThat(resultado).isEmpty();
    }

    // -----------------------------------------------------------------------
    // buscar() — orden descendente por fecha
    // -----------------------------------------------------------------------

    @Test
    void buscar_retornaOrdenadoPorFechaDescendente() {
        Consulta antigua = crearConsulta("Consulta antigua");
        antigua.setFecha(LocalDate.of(2023, 1, 1));
        em.persist(antigua);

        Consulta reciente = crearConsulta("Consulta reciente");
        reciente.setFecha(LocalDate.of(2025, 6, 15));
        em.persist(reciente);

        em.flush();

        List<Consulta> resultado = consultaRepository.buscar(null);

        assertThat(resultado.get(0).getFecha())
                .isAfterOrEqualTo(resultado.get(1).getFecha());
    }
}