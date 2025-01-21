package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Recensioni;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewRecensioneDTO;
import dianafriptuleac.capstone_flavor_love.services.RecensioniService;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/recensioni")
public class RecensioniController {

    @Autowired
    private RecensioniService recensioniService;

    //salvo recensione per ricetta
    @PostMapping("/ricette/{ricettaId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Recensioni saveRecensione(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @RequestBody @Validated NewRecensioneDTO newRecensioneDTO) {
        return recensioniService.saveRecensione(ricettaId, newRecensioneDTO, currentAuthenticatedUser);
    }

    //cancello recensione
    @DeleteMapping("/{recensioneId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecensione(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID recensioneId) {
        recensioniService.deleteRecensione(recensioneId, currentAuthenticatedUser, "ADMIN");
    }


    //modifico la recensione
    @PutMapping("/{recensioneId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Recensioni updateRecensione(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID recensioneId,
            @RequestBody @Validated NewRecensioneDTO newRecensioneDTO) {
        return recensioniService.updateRecensione(recensioneId, newRecensioneDTO, currentAuthenticatedUser, "ADMIN");

    }

    //get di tutte le recensioni per ricetta
    @GetMapping("/ricette/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Page<Recensioni> getRecensioniByRicetta(
            @PathVariable UUID ricettaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataCreazione") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return recensioniService.getRecensioniByRicetta(ricettaId, pageable);
    }

    //firltro recensioni per nr stelle
    //api/recensioni/ricette/{ricettaId}/filtra?stelle=5&page=0&size=5&sortBy=dataCreazione
    @GetMapping("/ricette/{ricettaId}/filtra")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Page<Recensioni> getRecensioniByRicettaAndStelle(
            @PathVariable UUID ricettaId,
            @RequestParam double stelle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataCreazione") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return recensioniService.getRecensioniByRicettaAndStelle(ricettaId, stelle, pageable);
    }
}
