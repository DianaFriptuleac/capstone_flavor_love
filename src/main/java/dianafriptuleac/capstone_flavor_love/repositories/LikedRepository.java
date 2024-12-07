package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LikedRepository extends JpaRepository<Liked, UUID> {
    Optional<Liked> findByUtenteId(UUID utenteId);
}
