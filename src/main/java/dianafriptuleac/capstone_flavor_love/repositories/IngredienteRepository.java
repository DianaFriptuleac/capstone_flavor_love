package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredienteRepository extends JpaRepository<Ingrediente, UUID> {

}
