package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.ListaSpesaElement;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.repositories.ListaSpesaElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListaSpesaElementService {

    @Autowired
    private ListaSpesaElementRepository listaSpesaElementReposiitory;

    public ListaSpesaElement findById(UUID ingredienteId) {
        return this.listaSpesaElementReposiitory.findById(ingredienteId)
                .orElseThrow(() -> new NotFoundException("L'ingrediente con id: " +
                        ingredienteId + " non è stato trovato!"));
    }

    public void findByIdAndDelete(UUID ingredienteId) {
        ListaSpesaElement foundElement = this.findById(ingredienteId);
        this.listaSpesaElementReposiitory.delete(foundElement);
    }

}
