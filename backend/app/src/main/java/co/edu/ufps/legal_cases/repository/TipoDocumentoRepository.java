package co.edu.ufps.legal_cases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.legal_cases.model.TipoDocumento;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, String> {

    boolean existsByDisplayNameIgnoreCase(String displayName);

    //¿Hay otro registro con este nombre distinto al que estoy editando?
    boolean existsByDisplayNameIgnoreCaseAndIdNot(String displayName, String id);

    List<TipoDocumento> findByActivoTrue();
}