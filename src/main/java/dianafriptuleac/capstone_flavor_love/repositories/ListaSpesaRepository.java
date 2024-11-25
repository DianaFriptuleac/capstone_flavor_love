package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.ListaSpesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ListaSpesaRepository extends JpaRepository<ListaSpesa, UUID> {
    Optional<ListaSpesa> findByUtenteId(UUID utenteId);
}
