package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Recensioni;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecensioniRepository extends JpaRepository<Recensioni, UUID> {
    //cerco tutte le recensioni della ricetta
    Page<Recensioni> findByRicetta(Ricetta ricetta, Pageable pageable);

    //filtro le recensioni per stelle
    Page<Recensioni> findByRicettaAndStelle(Ricetta ricetta, double stelle, Pageable pageable);
}
