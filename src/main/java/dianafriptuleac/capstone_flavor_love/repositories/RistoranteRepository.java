package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Ristoranti;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RistoranteRepository extends JpaRepository<Ristoranti, UUID> {
    //cerco per nome e indirizzo
    Optional<Ristoranti> findByNomeAndIndirizzo(String nome, String indirizzo);

    //cerco per nome
    Page<Ristoranti> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    //cerco per citta
    Page<Ristoranti> findByCittaContainingIgnoreCase(String citta, Pageable pageable);

    //cerco per categorie
    Page<Ristoranti> findByCategorieContainingIgnoreCase(String categorie, Pageable pageable);

    //query per cercare x nome, citta e categorie
    @Query("SELECT r FROM Ristoranti r WHERE " +
            "LOWER(r.nome) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.citta) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.categorie) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Ristoranti> findByNomeOrCittaOrCategorie(String keyword, Pageable pageable);

    //LOWER-> coverte in minuscolo
    //:keyword = "sole" ->  il pattern sara %sole% -> "Trattoria Sole"
}
