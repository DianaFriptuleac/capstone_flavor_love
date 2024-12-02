package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Ingrediente;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewIngredienteDTO;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettaDTO;
import dianafriptuleac.capstone_flavor_love.services.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;


    //----------------------------- CRUD Ricette ----------------------------------
    // Creo ricetta
    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ricetta createRicetta(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                                 @RequestBody @Validated NewRicettaDTO newRicettaDTO) {
        return ricettaService.saveRicetta(newRicettaDTO, currentAuthenticatedUser);
    }

    // Get di tutte le ricette
    @GetMapping
    public Page<Ricetta> getAllRicette(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String sortBy) {
        return ricettaService.findAll(page, size, sortBy);
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
    public Ricetta addIngrediente(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @RequestBody @Validated NewIngredienteDTO newIngredienteDTO) {
        return ricettaService.aggIngredienti(ricettaId, newIngredienteDTO, currentAuthenticatedUser);
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

}
