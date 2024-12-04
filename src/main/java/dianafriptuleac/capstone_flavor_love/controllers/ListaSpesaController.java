package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.ListaSpesa;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.payloads.NewListaSpesaElementDTO;
import dianafriptuleac.capstone_flavor_love.services.ListaSpesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/lista-spesa")
public class ListaSpesaController {

    @Autowired
    private ListaSpesaService listaSpesaService;

  /*  //Agg. ingredienti da ricette
    @PostMapping("/{ricettaId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ListaSpesa aggiungiIngredientiDaRicetta(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ricettaId) {
        return listaSpesaService.addIngredientidaRicetta(ricettaId, currentAuthenticatedUser);
    }
*/

    //Modifico quantita ingredienti
    @PutMapping("/ingrediente/{ingredienteId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ListaSpesa updateQuantita(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ingredienteId,
            @RequestBody @Validated NewListaSpesaElementDTO updatedElementDTO) {
        return listaSpesaService.updateQuantitaIngredienti(ingredienteId, updatedElementDTO, currentAuthenticatedUser);
    }

    //Elimino ingrediente dalla lista
    @DeleteMapping("/ingrediente/{ingredienteId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngrediente(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @PathVariable UUID ingredienteId) {
        listaSpesaService.removeIngrediente(ingredienteId, currentAuthenticatedUser);
    }

    // Recupero la lista della spesa
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ListaSpesa getListaSpesa(
            @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return listaSpesaService.getListaSpesaByUtente(currentAuthenticatedUser);
    }
}
