package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.ListaSpesa;
import dianafriptuleac.capstone_flavor_love.entities.ListaSpesaElement;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewListaSpesaElementDTO;
import dianafriptuleac.capstone_flavor_love.repositories.ListaSpesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListaSpesaService {
    @Autowired
    private ListaSpesaRepository listaSpesaRepository;

    @Autowired
    private ListaSpesaElementService listaSpesaElementService;

    @Autowired
    private RicettaService ricettaService;

    //Ottengo la lista spesa collegata all'utente
    public ListaSpesa getListaSpesaByUtente(Utente utente) {
        return listaSpesaRepository.findByUtenteId(utente.getId())
                .orElseGet(() -> {
                    ListaSpesa nuovaLista = new ListaSpesa();
                    nuovaLista.setUtente(utente);
                    return listaSpesaRepository.save(nuovaLista);
                });
    }

    //Aggiungo gli ingredienti
 /*   public ListaSpesa addIngredientidaRicetta(UUID ricettaId, Utente utente) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        ListaSpesa listaSpesa = getListaSpesaByUtente(utente);

        for (Ingrediente ingrediente : ricetta.getIngredienti()) {
            ListaSpesaElement element = new ListaSpesaElement(
                    ingrediente.getNome(),
                    ingrediente.getDosaggio(),
                    ingrediente.getSezione(),
                    1,
                    ricetta// ->  Quantitativo iniziale
            );
            listaSpesa.addElement(element);

        }
        return listaSpesaRepository.save(listaSpesa);
    }
*/
    // Modifico la quantita degli ingredienti
    public ListaSpesa updateQuantitaIngredienti(UUID ingredientiId, NewListaSpesaElementDTO updatedElementDTO, Utente utente) {
        ListaSpesaElement element = listaSpesaElementService.findById(ingredientiId);
        if (!element.getListaSpesa().getUtente().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare la lista spesa!");
        }
        element.setNome(updatedElementDTO.nome());
        element.setDosaggio(updatedElementDTO.dosaggio());
        element.setSezione(updatedElementDTO.sezione());
        element.setQuantita(updatedElementDTO.quantita());

        return listaSpesaRepository.save(element.getListaSpesa());
    }

    //elimiino un ingrediente dalla lista
    public void removeIngrediente(UUID ingredienteId, Utente utente) {
        ListaSpesaElement element = listaSpesaElementService.findById(ingredienteId);

        if (!element.getListaSpesa().getUtente().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per cancellare un ingrediente dalla lista della spesa!");
        }
        listaSpesaElementService.findByIdAndDelete(ingredienteId);
    }
}
