package co.edu.ufps.legal_cases.repository;

import co.edu.ufps.legal_cases.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

    boolean existsByNombreAndAreaId(String nombre, Long areaId);

    List<Tema> findByAreaId(Long areaId);
}
