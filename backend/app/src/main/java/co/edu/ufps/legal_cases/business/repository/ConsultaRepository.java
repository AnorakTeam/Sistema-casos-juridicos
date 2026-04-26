package co.edu.ufps.legal_cases.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.ufps.legal_cases.business.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /**
     * Busca consultas cuya descripción, nombre, apellido o número de documento
     * de la persona contengan el texto indicado (búsqueda insensible a mayúsculas).
     * Si el texto está vacío o es null, retorna todas las consultas.
     */
    @Query("""
            SELECT c FROM Consulta c
            JOIN c.persona p
            WHERE :search IS NULL OR :search = ''
               OR LOWER(c.descripcion)       LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.nombres)           LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.apellidos)         LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.numeroDocumento)   LIKE LOWER(CONCAT('%', :search, '%'))
            ORDER BY c.fecha DESC
            """)
    List<Consulta> buscar(@Param("search") String search);
}