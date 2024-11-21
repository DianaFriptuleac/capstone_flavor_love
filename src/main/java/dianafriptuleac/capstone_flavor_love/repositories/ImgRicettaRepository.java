package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.ImgRicetta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImgRicettaRepository extends JpaRepository<ImgRicetta, UUID> {
}