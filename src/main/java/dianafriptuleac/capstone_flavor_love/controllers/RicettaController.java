package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettaDTO;
import dianafriptuleac.capstone_flavor_love.services.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;


    // Creo ricetta
    @PostMapping
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("isAuthenticated()")
    public Ricetta updateRicetta(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @RequestBody @Validated NewRicettaDTO newRicettaDTO) {

        return ricettaService.updateRicetta(ricettaId, newRicettaDTO, currentAuthenticatedUser, "ADMIN");
    }

    // Cancella ricetta (solo per creatore o ADMIN)
    @DeleteMapping("/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRicetta(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId) {

        ricettaService.deleteRicetta(ricettaId, currentAuthenticatedUser, "ADMIN");
    }
}
