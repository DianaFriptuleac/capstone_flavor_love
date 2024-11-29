package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.RicetteEsterne;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.services.RicetteEsterneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ricetteEsterne")
public class RicetteEsterneController {

    @Autowired
    private RicetteEsterneService ricetteEsterneService;

    @GetMapping("/fetchAll")
    public Page<RicetteEsterne> fetchAndSaveAllRicette(@RequestParam String query, Pageable pageable) {
        return ricetteEsterneService.fetchAndSaveAllRicette(query, pageable);
    }

    @GetMapping("/allRicette")
    public Page<RicetteEsterne> getAllRicette(Pageable pageable) {
        return ricetteEsterneService.getAllRicetteSalvate(pageable);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRicettaEsterna(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID id) {

        ricetteEsterneService.removeRicetta(id, currentAuthenticatedUser, "ADMIN");
    }
}
