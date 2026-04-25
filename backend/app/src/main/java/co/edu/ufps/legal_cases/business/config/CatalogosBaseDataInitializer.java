package co.edu.ufps.legal_cases.business.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import co.edu.ufps.legal_cases.business.model.Sede;
import co.edu.ufps.legal_cases.business.model.TipoDocumento;
import co.edu.ufps.legal_cases.business.repository.SedeRepository;
import co.edu.ufps.legal_cases.business.repository.TipoDocumentoRepository;

@Configuration
public class CatalogosBaseDataInitializer {

    @Bean
    @Order(2)
    CommandLineRunner initCatalogosBase(
            TipoDocumentoRepository tipoDocumentoRepository,
            SedeRepository sedeRepository) {
        return args -> {
            inicializarTiposDocumento(tipoDocumentoRepository);
            inicializarSedes(sedeRepository);
        };
    }

    private void inicializarTiposDocumento(TipoDocumentoRepository tipoDocumentoRepository) {
        if (tipoDocumentoRepository.count() > 0) {
            return;
        }

        TipoDocumento cc = new TipoDocumento();
        cc.setDisplayName("CC");
        cc.setActivo(true);
        tipoDocumentoRepository.save(cc);

        TipoDocumento ti = new TipoDocumento();
        ti.setDisplayName("TI");
        ti.setActivo(true);
        tipoDocumentoRepository.save(ti);

        TipoDocumento ce = new TipoDocumento();
        ce.setDisplayName("CE");
        ce.setActivo(true);
        tipoDocumentoRepository.save(ce);

        TipoDocumento nit = new TipoDocumento();
        nit.setDisplayName("NIT");
        nit.setActivo(true);
        tipoDocumentoRepository.save(nit);
    }

    private void inicializarSedes(SedeRepository sedeRepository) {
        if (sedeRepository.count() > 0) {
            return;
        }

        Sede principal = new Sede();
        principal.setNombre("Sede principal");
        sedeRepository.save(principal);

        Sede consultorio = new Sede();
        consultorio.setNombre("Consultorio jurídico");
        sedeRepository.save(consultorio);
    }
}