package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RicettaRepository extends JpaRepository<Ricetta, UUID> {
    Page<Ricetta> findByTitoloContainingIgnoreCase(String titolo, Pageable pageable);

    // carico la ricetta includento l'immagine
    @Query("SELECT r FROM Ricetta r JOIN FETCH r.img WHERE r.id = :id")
    Optional<Ricetta> findByIdWithImages(@Param("id") UUID id);

}
