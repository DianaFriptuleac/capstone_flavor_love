package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Liked;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.services.LikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/liked")
public class LikedController {

    @Autowired
    private LikedService likedService;

    @PostMapping("/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    public Liked addRicettaToLiked(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId) {
        return likedService.addRicettaToLiked(ricettaId, currentAuthenticatedUser);
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Liked getLiked(
            @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return likedService.getLikedByUtente(currentAuthenticatedUser);
    }

    @DeleteMapping("/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRicettaFromLiked(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId) {
        likedService.removeRicettaFromLiked(ricettaId, currentAuthenticatedUser);
    }
}
