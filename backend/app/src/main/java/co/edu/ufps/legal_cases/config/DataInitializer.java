package co.edu.ufps.legal_cases.config;

import co.edu.ufps.legal_cases.model.Area;
import co.edu.ufps.legal_cases.model.Tema;
import co.edu.ufps.legal_cases.model.Tipo;
import co.edu.ufps.legal_cases.repository.AreaRepository;
import co.edu.ufps.legal_cases.repository.TemaRepository;
import co.edu.ufps.legal_cases.repository.TipoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            AreaRepository areaRepository,
            TemaRepository temaRepository,
            TipoRepository tipoRepository
    ) {
        return args -> {
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
        };
    }
}