package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Ricettario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RicettarioRepository extends JpaRepository<Ricettario, UUID> {
    Page<Ricettario> findByUtenteId(UUID utenteId, Pageable pageable);

    // ignora il case e  trova anche per pezzo di nome
    Optional<Ricettario> findByNomeContainingIgnoreCaseAndUtenteId(String nome, UUID utente);


}
