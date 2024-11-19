package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettaDTO;
import dianafriptuleac.capstone_flavor_love.services.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ricette")
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public Ricetta createRicetta(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                                 @RequestBody @Validated NewRicettaDTO newRicettaDTO) {
        return ricettaService.saveRicetta(newRicettaDTO, currentAuthenticatedUser.getId());
    }


}
