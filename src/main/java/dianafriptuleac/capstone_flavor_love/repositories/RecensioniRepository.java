package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Recensioni;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecensioniRepository extends JpaRepository<Recensioni, UUID> {
}
