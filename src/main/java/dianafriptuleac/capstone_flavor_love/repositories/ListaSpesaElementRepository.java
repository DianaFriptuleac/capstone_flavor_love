package dianafriptuleac.capstone_flavor_love.repositories;

import dianafriptuleac.capstone_flavor_love.entities.ListaSpesaElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListaSpesaElementRepository extends JpaRepository<ListaSpesaElement, UUID> {

}
