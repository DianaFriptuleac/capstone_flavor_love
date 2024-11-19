package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RicettaRepository extends JpaRepository<Ricetta, UUID> {
}
