package co.edu.ufps.legal_cases.repository;

import co.edu.ufps.legal_cases.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {

    boolean existsByNombreAndTemaId(String nombre, Long temaId);

    List<Tipo> findByTemaId(Long temaId);
}