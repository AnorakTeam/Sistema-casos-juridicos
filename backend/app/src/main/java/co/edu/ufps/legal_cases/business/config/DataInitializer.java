package co.edu.ufps.legal_cases.business.config;

import co.edu.ufps.legal_cases.business.dto.PersonaDTO;
import co.edu.ufps.legal_cases.business.model.Area;
import co.edu.ufps.legal_cases.business.model.Tema;
import co.edu.ufps.legal_cases.business.model.Tipo;
import co.edu.ufps.legal_cases.business.repository.AreaRepository;
import co.edu.ufps.legal_cases.business.repository.PersonaRepository;
import co.edu.ufps.legal_cases.business.repository.TemaRepository;
import co.edu.ufps.legal_cases.business.repository.TipoRepository;
import co.edu.ufps.legal_cases.business.service.PersonaService;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DataInitializer {

    @Bean
    @Order(2)
    CommandLineRunner initData(
            AreaRepository areaRepository,
            TemaRepository temaRepository,
            TipoRepository tipoRepository,
            PersonaRepository personaRepository,
            PersonaService personaService) {
        return args -> {
            inicializarAreasTemasYTipos(areaRepository, temaRepository, tipoRepository);
            inicializarPersonas(personaRepository, personaService);
        };
    }

    private void inicializarAreasTemasYTipos(
            AreaRepository areaRepository,
            TemaRepository temaRepository,
            TipoRepository tipoRepository) {
        if (areaRepository.count() > 0) {
            return;
        }

        // Áreas
        Area civil = new Area();
        civil.setNombre("Civil");
        civil = areaRepository.save(civil);

        Area penal = new Area();
        penal.setNombre("Penal");
        penal = areaRepository.save(penal);

        Area laboral = new Area();
        laboral.setNombre("Laboral");
        laboral = areaRepository.save(laboral);

        // Temas
        Tema contratos = new Tema();
        contratos.setNombre("Contratos");
        contratos.setArea(civil);
        contratos = temaRepository.save(contratos);

        Tema sucesiones = new Tema();
        sucesiones.setNombre("Sucesiones");
        sucesiones.setArea(civil);
        sucesiones = temaRepository.save(sucesiones);

        Tema lesiones = new Tema();
        lesiones.setNombre("Lesiones personales");
        lesiones.setArea(penal);
        lesiones = temaRepository.save(lesiones);

        Tema despidos = new Tema();
        despidos.setNombre("Despidos");
        despidos.setArea(laboral);
        despidos = temaRepository.save(despidos);

        // Tipos
        Tipo incumplimiento = new Tipo();
        incumplimiento.setNombre("Incumplimiento contractual");
        incumplimiento.setTema(contratos);
        tipoRepository.save(incumplimiento);

        Tipo terminacion = new Tipo();
        terminacion.setNombre("Terminación contractual");
        terminacion.setTema(contratos);
        tipoRepository.save(terminacion);

        Tipo herencia = new Tipo();
        herencia.setNombre("Herencia");
        herencia.setTema(sucesiones);
        tipoRepository.save(herencia);

        Tipo culpa = new Tipo();
        culpa.setNombre("Culpa médica");
        culpa.setTema(lesiones);
        tipoRepository.save(culpa);

        Tipo injustificado = new Tipo();
        injustificado.setNombre("Despido injustificado");
        injustificado.setTema(despidos);
        tipoRepository.save(injustificado);
    }

    private void inicializarPersonas(
            PersonaRepository personaRepository,
            PersonaService personaService) {
        if (personaRepository.count() > 0) {
            return;
        }

        // crear personas usando personaService
        // Persona mayor de edad con telefono y correo
        personaService.crear(PersonaDTO.builder()
                .tipoUsuario("Consultante")
                .tipoDocumento("CC")
                .numeroDocumento("1001001001")
                .fechaExpedicion(LocalDate.of(2020, 1, 15))
                .ciudadExpedicion("Cucuta")
                .nombres("Juan")
                .apellidos("Perez")
                .nombreIdentitario("Juan Perez")
                .pronombre("El")
                .sexo("Masculino")
                .genero("Masculino")
                .orientacionSexual("Heterosexual")
                .fechaNacimiento(LocalDate.of(2000, 5, 10))
                .telefono("3001234567")
                .correo("juan.perez@example.com")
                .nacionalidad("Colombiana")
                .estadoCivil("Soltero")
                .escolaridad("Universitario")
                .grupoEtnico("No informa")
                .condicionActual("Empleado")
                .sabeLeerEscribir(true)
                .discapacidad("No informa")
                .caracterizacionPcd("No aplica")
                .necesitaAjustePcd(false)
                .departamento("Norte de Santander")
                .municipio("Cucuta")
                .barrio("Centro")
                .direccion("Calle 10 # 5-20")
                .comuna("Comuna 1")
                .localidad("Urbana")
                .estrato(3)
                .tipoVivienda("Casa")
                .zona("Urbana")
                .tenencia("Propia")
                .numeroPersonasACargo(0)
                .ingresosAdicionales(false)
                .energiaElectrica(true)
                .acueducto(true)
                .alcantarillado(true)
                .ocupacion("Ingeniero")
                .empresa("Empresa X")
                .salario(2000000)
                .cargo("Analista")
                .direccionEmpresa("Av 1 # 2-30")
                .telefonoEmpresa("6071234567")
                .nombreCompletoAcudiente(null)
                .relacionAcudiente(null)
                .telefonoAcudiente(null)
                .correoAcudiente(null)
                .direccionAcudiente(null)
                .comoSeEntero("Redes sociales")
                .relacionConUniversidad("Ninguna")
                .build());

        // Persona mayor de edad solo con telefono
        personaService.crear(PersonaDTO.builder()
                .tipoUsuario("Consultante")
                .tipoDocumento("CC")
                .numeroDocumento("1001001002")
                .fechaExpedicion(LocalDate.of(2019, 7, 20))
                .ciudadExpedicion("Cucuta")
                .nombres("Maria")
                .apellidos("Lopez")
                .nombreIdentitario("Maria Lopez")
                .pronombre("Ella")
                .sexo("Femenino")
                .genero("Femenino")
                .orientacionSexual("Heterosexual")
                .fechaNacimiento(LocalDate.of(1998, 3, 12))
                .telefono("3002223344")
                .correo(null)
                .nacionalidad("Colombiana")
                .estadoCivil("Soltera")
                .escolaridad("Tecnica")
                .grupoEtnico("No informa")
                .condicionActual("Independiente")
                .sabeLeerEscribir(true)
                .discapacidad("No informa")
                .caracterizacionPcd("No aplica")
                .necesitaAjustePcd(false)
                .departamento("Norte de Santander")
                .municipio("Cucuta")
                .barrio("La Playa")
                .direccion("Calle 8 # 3-10")
                .comuna("Comuna 2")
                .localidad("Urbana")
                .estrato(2)
                .tipoVivienda("Apartamento")
                .zona("Urbana")
                .tenencia("Arriendo")
                .numeroPersonasACargo(1)
                .ingresosAdicionales(true)
                .energiaElectrica(true)
                .acueducto(true)
                .alcantarillado(true)
                .ocupacion("Comerciante")
                .empresa("Negocio propio")
                .salario(1500000)
                .cargo("Propietaria")
                .direccionEmpresa("Carrera 4 # 12-22")
                .telefonoEmpresa("6073344556")
                .nombreCompletoAcudiente(null)
                .relacionAcudiente(null)
                .telefonoAcudiente(null)
                .correoAcudiente(null)
                .direccionAcudiente(null)
                .comoSeEntero("Amigo")
                .relacionConUniversidad("Ninguna")
                .build());

        // Persona menor de edad con acudiente
        personaService.crear(PersonaDTO.builder()
                .tipoUsuario("Consultante")
                .tipoDocumento("TI")
                .numeroDocumento("2002002001")
                .fechaExpedicion(LocalDate.of(2023, 2, 10))
                .ciudadExpedicion("Cucuta")
                .nombres("Sofia")
                .apellidos("Ramirez")
                .nombreIdentitario("Sofia Ramirez")
                .pronombre("Ella")
                .sexo("Femenino")
                .genero("Femenino")
                .orientacionSexual("No informa")
                .fechaNacimiento(LocalDate.of(2010, 8, 15))
                .telefono("3005556677")
                .correo(null)
                .nacionalidad("Colombiana")
                .estadoCivil("Soltera")
                .escolaridad("Secundaria")
                .grupoEtnico("No informa")
                .condicionActual("Estudiante")
                .sabeLeerEscribir(true)
                .discapacidad("No informa")
                .caracterizacionPcd("No aplica")
                .necesitaAjustePcd(false)
                .departamento("Norte de Santander")
                .municipio("Cucuta")
                .barrio("San Luis")
                .direccion("Calle 7 # 2-18")
                .comuna("Comuna 1")
                .localidad("Urbana")
                .estrato(1)
                .tipoVivienda("Casa")
                .zona("Urbana")
                .tenencia("Familiar")
                .numeroPersonasACargo(0)
                .ingresosAdicionales(false)
                .energiaElectrica(true)
                .acueducto(true)
                .alcantarillado(true)
                .ocupacion("Estudiante")
                .empresa("No aplica")
                .salario(0)
                .cargo("No aplica")
                .direccionEmpresa("No aplica")
                .telefonoEmpresa("No aplica")
                .nombreCompletoAcudiente("Ana Ramirez")
                .relacionAcudiente("Madre")
                .telefonoAcudiente("3019998877")
                .correoAcudiente(null)
                .direccionAcudiente("Calle 7 # 2-18")
                .comoSeEntero("Colegio")
                .relacionConUniversidad("Ninguna")
                .build());

    }
}