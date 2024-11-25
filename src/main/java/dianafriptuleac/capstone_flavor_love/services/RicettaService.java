package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.CategoriaRicetta;
import dianafriptuleac.capstone_flavor_love.entities.Ingrediente;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewIngredienteDTO;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettaDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RicettaService {

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private CategorieRicettaService categorieRicettaService;

    @Autowired
    @Lazy
    private IngredienteService ingredienteService;

    @Autowired
    private ListaSpesaElementService listaSpesaElementService;


    // -------------------------------- Creo la ricetta ----------------------------
    public Ricetta saveRicetta(NewRicettaDTO newRicettaDTO, Utente utente) {
        Ricetta ricetta = new Ricetta(
                newRicettaDTO.titolo(),
                newRicettaDTO.procedimento(),
                newRicettaDTO.difficoltaRicetta(),
                newRicettaDTO.tempoPreparazioneMinuti(),
                newRicettaDTO.tempoCotturaMinuti(),
                newRicettaDTO.costoRicetta(),
                utente);
        //Recupero le categorie e li associo alla ricetta
        List<CategoriaRicetta> categorie = newRicettaDTO.nomeCategorieRicette().stream()
                .map(nome -> categorieRicettaService.findByNome(nome))
                .toList();
        ricetta.setCategorie(categorie);
        return ricettaRepository.save(ricetta);
    }


    //----------------------------------Update Ricetta --------------------------
    public Ricetta updateRicetta(UUID ricettaId, NewRicettaDTO newRicettaDTO, Utente utente, String adminRole) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException("Ricetta con ID " + ricettaId + " non trovata!"));

        // Controllo se l'utente è il creatore o ADMIN
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(adminRole));
        if (!ricetta.getUtente().getId().equals(utente.getId()) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per modificare questa ricetta!");
        }

        ricetta.setTitolo(newRicettaDTO.titolo());
        ricetta.setProcedimento(newRicettaDTO.procedimento());
        ricetta.setDifficoltaRicetta(newRicettaDTO.difficoltaRicetta());
        ricetta.setTempoPreparazioneMinuti(newRicettaDTO.tempoPreparazioneMinuti());
        ricetta.setTempoCotturaMinuti(newRicettaDTO.tempoCotturaMinuti());
        ricetta.setCostoRicetta(newRicettaDTO.costoRicetta());

        List<CategoriaRicetta> categorie = new ArrayList<>(newRicettaDTO.nomeCategorieRicette().stream()
                .map(nome -> categorieRicettaService.findByNome(nome))
                .toList());
        ricetta.setCategorie(categorie);

        return ricettaRepository.save(ricetta);
    }

    //--------------------------------Cancello Ricetta ------------------------------
    public void deleteRicetta(UUID ricettaId, Utente currentAuthenticatedUser, String adminRole) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException("Ricetta con ID " + ricettaId + " non trovata!"));

        // Controllo se l'utente è admin o creatore
        boolean isAdmin = currentAuthenticatedUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(adminRole));

        if (!ricetta.getUtente().getId().equals(currentAuthenticatedUser.getId()) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questa ricetta!");

        }
        ricettaRepository.delete(ricetta);
    }

    //------------------------------------find all ricette -----------------------
    public Page<Ricetta> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ricettaRepository.findAll(pageable);
    }

    //-------------------------------------find per nome ricetta ---------------------------
    public Page<Ricetta> findByName(String titolo, int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Ricetta> ricettaFound = this.ricettaRepository.findByTitoloContainingIgnoreCase(titolo, pageable);
        if (ricettaFound.isEmpty()) {

            throw new NotFoundException("Nessun elemento trovato per la tua ricerca: " + titolo);
        }
        return ricettaFound;
    }

    //-----------------------------Controllo se l'utente e il Creatore della ricetta o l'Admin
    public boolean isCreatorOrAdmin(UUID ricettaId, UUID userId, boolean isAdmin) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException("Ricetta con l'ID " + ricettaId + " non è stata trovata!"));
        return ricetta.getUtente().getId().equals(userId) || isAdmin;
    }

    //-------------------------------find by Id ricetta ------------------------------------
    public Ricetta findById(UUID ricettaId) {
        return this.ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException(String.valueOf(ricettaId)));
    }

    //---------------------------Aggiungo ingredienti----------------------
    public Ricetta aggIngredienti(UUID ricettaId, NewIngredienteDTO newIngredienteDTO, Utente utente) {
        ingredienteService.aggIngrediente(ricettaId, newIngredienteDTO, utente);
        return findById(ricettaId);
    }

    //-------------------------Elimino l'ingrediente dalla ricetta --------------------
    public void deleteIngrediente(UUID ricettaId, UUID ingredienteId, Utente utente) {
        ingredienteService.deleteIngrediente(ricettaId, ingredienteId, utente);
    }

    //--------------------------Modifico Ingrediente ----------------------
    public Ricetta updateRicettaIngr(UUID ricettaId, UUID ingredienteId,
                                     NewIngredienteDTO newIngredienteDTO, Utente utente) {
        ingredienteService.updateIngrediente(ingredienteId, newIngredienteDTO, utente);
        return findById(ricettaId);
    }


    //----------------------- Raggruppo ingredienti per sezione ----------------
    public Map<String, List<Ingrediente>> getIngrBySezione(UUID ricettaId) {
        Ricetta ricetta = findById(ricettaId);

        return ricetta.getIngredienti().stream()
                .collect(Collectors.groupingBy(Ingrediente::getSezione));
    }

}
