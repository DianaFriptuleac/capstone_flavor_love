package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.Ingrediente;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewIngredienteDTO;
import dianafriptuleac.capstone_flavor_love.repositories.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    @Lazy
    private RicettaService ricettaService;

    //Verifico utente
    private void verificaAdminOCreatore(Ricetta ricetta, Utente utente) {
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!ricetta.getUtente().getId().equals(utente.getId()) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per modificare questa ricetta!");
        }
    }

    // Agg. ingrediente
    public Ingrediente aggIngrediente(UUID ricettaId, NewIngredienteDTO newIngredienteDTO, Utente utente) {
        Ricetta ricetta = ricettaService.findById(ricettaId);

        verificaAdminOCreatore(ricetta, utente);
        Ingrediente ingrediente = new Ingrediente(
                newIngredienteDTO.nome(),
                newIngredienteDTO.dosaggio(),
                newIngredienteDTO.sezione()
        );
        ingrediente.setRicetta(ricetta);
        ricetta.getIngredienti().add(ingrediente);

        return ingredienteRepository.save(ingrediente);
    }

    //elimino ingrediente
    public void deleteIngrediente(UUID ricettaId, UUID ingredienteId, Utente utente) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        verificaAdminOCreatore(ricetta, utente);
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId).orElseThrow(() ->
                new NotFoundException("L'ingrediente con id: " + ingredienteId + " non è stato trovato!"));
        ricetta.getIngredienti().remove(ingrediente);
        ingrediente.setRicetta(null);
        ingredienteRepository.delete(ingrediente);
    }

    //Modifico l'ingrediente
    public Ingrediente updateIngrediente(UUID ingredienteId, NewIngredienteDTO newIngredienteDTO, Utente utente) {
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId).orElseThrow(() ->
                new NotFoundException("L'ingrediente con id: " + ingredienteId + " non è stato trovato!"));
        //Prendo la ricetta associata all'ingrediente
        Ricetta ricetta = ingrediente.getRicetta();

        verificaAdminOCreatore(ricetta, utente);
        ingrediente.setNome(newIngredienteDTO.nome());
        ingrediente.setDosaggio(newIngredienteDTO.dosaggio());
        ingrediente.setSezione(newIngredienteDTO.sezione());

        return ingredienteRepository.save(ingrediente);
    }


}
