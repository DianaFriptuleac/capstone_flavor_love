package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.RicetteEsterne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RicetteEsterneRepository extends JpaRepository<RicetteEsterne, UUID> {
    // Cerco per titolo (ignoro il case)
    @Query("SELECT r FROM RicetteEsterne r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<RicetteEsterne> searchByTitle(@Param("query") String query, Pageable pageable);
}
