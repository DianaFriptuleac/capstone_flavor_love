package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.RicetteEsterne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RicetteEsterneRepository extends JpaRepository<RicetteEsterne, UUID> {
}
