package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.enums.RuoloUtente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByEmail(String email);

    Utente findByRuolo(RuoloUtente ruolo);
}
