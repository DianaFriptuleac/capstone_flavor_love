package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Ricettario;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettarioDTO;
import dianafriptuleac.capstone_flavor_love.payloads.RispostaRicettarioDTO;
import dianafriptuleac.capstone_flavor_love.services.RicettarioService;
import dianafriptuleac.capstone_flavor_love.services.UtenteService;
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
@RequestMapping("/api/ricettari")
public class RicettarioController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RicettarioService ricettarioService;

    //Creo ricettario
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public Ricettario createRicettario(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                                       @RequestBody @Validated NewRicettarioDTO newRicettarioDTO) {
        return ricettarioService.saveRicettario(newRicettarioDTO, currentAuthenticatedUser);
    }

    //Add. ricetta
    @PostMapping("/{ricettarioId}/ricette/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    public Ricettario addRicetta(@PathVariable UUID ricettarioId,
                                 @PathVariable UUID ricettaId,
                                 @AuthenticationPrincipal Utente utente) {

        return ricettarioService.addRicettaToRicettario(ricettarioId, ricettaId, utente);
    }

    //Cerco ricettario per nome
    @GetMapping("/nome/{nome}")
    @PreAuthorize("isAuthenticated()")
    public Ricettario getRicettarioPerNome(@PathVariable String nome,
                                           @AuthenticationPrincipal Utente utente) {
        return ricettarioService.findByNome(nome, utente);
    }

    //Cerco tutti i ricettari
    @GetMapping
    public Page<Ricettario> getAllRicettari(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @AuthenticationPrincipal Utente utente) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ricettarioService.getRicettariPerUtente(utente, pageable);
    }

    //cerco ricettario con la lista ricette
    @GetMapping("/{id}")
    public RispostaRicettarioDTO getRicettarioConRicette(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal Utente utente) {
        Pageable pageable = PageRequest.of(page, size);
        return ricettarioService.getRicettarioConRicette(id, utente, pageable);
    }


    //Cancello una ricetta
    @DeleteMapping("/{ricettarioId}/ricette/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Ricettario removeRicettaFromRicettario(@PathVariable UUID ricettarioId,
                                                  @PathVariable UUID ricettaId,
                                                  @AuthenticationPrincipal Utente utente) {
        return ricettarioService.removeRicettaFromRicettario(ricettarioId, ricettaId, utente);
    }

    //Cancello intero ricettario
    @DeleteMapping("/{ricettarioId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRicettario(@PathVariable UUID ricettarioId,
                                 @AuthenticationPrincipal Utente utente) {
        ricettarioService.deleteRicettario(ricettarioId, utente);
    }


}
