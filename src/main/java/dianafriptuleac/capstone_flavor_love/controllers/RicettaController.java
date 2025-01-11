package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.ImgRicetta;
import dianafriptuleac.capstone_flavor_love.entities.Ingrediente;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewIngredienteDTO;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettaDTO;
import dianafriptuleac.capstone_flavor_love.services.ImgRicettaService;
import dianafriptuleac.capstone_flavor_love.services.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private ImgRicettaService imgRicettaService;


    //----------------------------- CRUD Ricette ----------------------------------
    // Creo ricetta
    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ricetta createRicetta(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                                 @RequestBody @Validated NewRicettaDTO newRicettaDTO) {
        return ricettaService.saveRicetta(newRicettaDTO, currentAuthenticatedUser);
    }

    @PostMapping("/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public ImgRicetta uploadImg(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                                @PathVariable UUID ricettaId,
                                @RequestParam("file") MultipartFile file) {
        return imgRicettaService.addImg(ricettaId, file, currentAuthenticatedUser);
    }


    // Get di tutte le ricette
    @GetMapping
    public Page<Ricetta> getAllRicette(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String sortBy) {
        System.out.println("Richiesta ricevuta all");
        return ricettaService.findAll(page, size, sortBy);
    }

    //Get per utente
    @GetMapping("/utente/{utenteId}")
    public Page<Ricetta> getRicetteByUtente(
            @PathVariable UUID utenteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String sortBy) {
        System.out.println("Richiesta ricevuta per l'utente con ID: {}");
        return ricettaService.findRicetteByUtente(utenteId, page, size, sortBy);
    }


    // Cerco ricetta per titolo
    @GetMapping("/cerca")
    public Page<Ricetta> getByName(
            @RequestParam String titolo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String sortBy) {
        return ricettaService.findByName(titolo, page, size, sortBy);
    }

    // cerco per id con immagine
    @GetMapping("/{id}")
    public Ricetta getRicetta(@PathVariable UUID id) {
        return ricettaService.getRicettaConImmagini(id);
    }

    //Aggiorno ricetta (solo per creatore o ADMIN)
    @PutMapping("/{ricettaId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Ricetta updateRicetta(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @RequestBody @Validated NewRicettaDTO newRicettaDTO) {

        return ricettaService.updateRicetta(ricettaId, newRicettaDTO, currentAuthenticatedUser, "ADMIN");
    }

    // Cancella ricetta (solo per creatore o ADMIN)
    @DeleteMapping("/{ricettaId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRicetta(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId) {

        ricettaService.deleteRicetta(ricettaId, currentAuthenticatedUser, "ADMIN");
    }

    //----------------------------- Gestione Ingredienti----------------------------------
    // Agg. ingrediente alla ricetta
    @PostMapping("/{ricettaId}/ingredienti")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ricetta addIngredienti(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @RequestBody @Validated List<NewIngredienteDTO> newIngredienti) {
        for (NewIngredienteDTO ingrediente : newIngredienti) {
            ricettaService.aggIngredienti(ricettaId, ingrediente, currentAuthenticatedUser);
        }
        return ricettaService.findById(ricettaId);
    }


    //Modifico ingrediente
    @PutMapping("/{ricettaId}/ingredienti/{ingredienteId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Ricetta updateIngrediiente(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @PathVariable UUID ingredienteId,
            @RequestBody @Validated NewIngredienteDTO newIngredienteDTO) {
        return ricettaService.updateRicettaIngr(ricettaId, ingredienteId, newIngredienteDTO, currentAuthenticatedUser);
    }

    //Cancello Ingrediente
    @DeleteMapping("/{ricettaId}/ingredienti/{ingredienteId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngrediente(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @PathVariable UUID ingredienteId) {
        ricettaService.deleteIngrediente(ricettaId, ingredienteId, currentAuthenticatedUser);
    }

    //Get ingredienti per sezione
    @GetMapping("/{ricettaId}/ingredienti/sezioni")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Map<String, List<Ingrediente>> getIngredientiBySezione(@PathVariable UUID ricettaId) {
        return ricettaService.getIngrBySezione(ricettaId);
    }

    // get lista ingredienti per ricetta
    @GetMapping("/{id}/ingredienti")
    public List<Ingrediente> getIngredienti(@PathVariable UUID id) {
        return ricettaService.getIngredientiByRicettaId(id);
    }

    //get ricetta per categorie
    @GetMapping("/categoria")
    public Page<Ricetta> getByCategorie(
            @RequestParam String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ricettaService.findRicetteByCategoria(categoria, pageable);
    }

}
