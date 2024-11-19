package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.CategoriaRicetta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRicettaRepository extends JpaRepository<CategoriaRicetta, UUID> {
    boolean existsByNome(String nome);

    Optional<CategoriaRicetta> findByNome(String nome);
}
