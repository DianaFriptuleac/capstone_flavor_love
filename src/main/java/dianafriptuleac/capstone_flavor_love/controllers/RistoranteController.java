package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Ristoranti;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewRistoranteDTO;
import dianafriptuleac.capstone_flavor_love.services.RistorantiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ristoranti")
public class RistoranteController {

    @Autowired
    private RistorantiService ristorantiService;

    //salva ristorante
    @PostMapping("/crea")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public Ristoranti creaRistorante(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @Validated @RequestBody NewRistoranteDTO newRistoranteDTO) {
        return ristorantiService.saveRistorante(newRistoranteDTO, currentAuthenticatedUser);
    }

    //modifico ristoranti
    @PutMapping("/{ristoranteId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Ristoranti updateRistorante(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ristoranteId,
            @RequestBody @Validated NewRistoranteDTO newRistoranteDTO) {
        return ristorantiService.updateRistorante(ristoranteId, newRistoranteDTO, currentAuthenticatedUser, "ADMIN");
    }

    //cancello ristorante
    @DeleteMapping("/{ristoranteId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaRistorante(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ristoranteId) {
        ristorantiService.deleteRistorante(ristoranteId, currentAuthenticatedUser, "ADMIN");
    }

    //get all ristoranti
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Ristoranti> getRistoranti(Pageable pageable) {
        return ristorantiService.getAllRistoranti(pageable);
    }

    //cerco per nome, citta o categorie
    @GetMapping("/cerca")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Page<Ristoranti> cercaRistoranti(
            @RequestParam("keyword") String keyword, Pageable pageable) {
        return ristorantiService.searchRistoranti(keyword, pageable);
    }
    //GET http://localhost:3001/api/ristoranti/cerca?keyword=Milano&page=0&size=10&sort=nome,asc
}
