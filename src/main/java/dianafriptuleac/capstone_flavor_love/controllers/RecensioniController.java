package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Recensioni;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewRecensioneDTO;
import dianafriptuleac.capstone_flavor_love.services.RecensioniService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/ricette/{ricettaId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Recensioni saveRecensione(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId,
            @RequestBody @Validated NewRecensioneDTO newRecensioneDTO) {
        return recensioniService.saveRecensione(ricettaId, newRecensioneDTO, currentAuthenticatedUser);
    }
}
