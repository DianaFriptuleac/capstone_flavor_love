package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.payloads.NewUtenteDTO;
import dianafriptuleac.capstone_flavor_love.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.findAll(page, size, sortBy);
    }


    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findById(@PathVariable UUID utenteId) {
        return this.utenteService.findById(utenteId);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable UUID id) {
        utenteService.findByIdAndDelete(id);
    }

    @GetMapping("/me")
    public Utente getMyProfile(@AuthenticationPrincipal Utente currentAuthenticateUtente) {
        Utente myDettagli = utenteService.findById(currentAuthenticateUtente.getId());
        return currentAuthenticateUtente;
    }

    @PutMapping("/me")
    public Utente updateMyProfile(@AuthenticationPrincipal Utente currentAuthenticateUtente,
                                  @RequestBody @Validated NewUtenteDTO body) {
        return this.utenteService.findByIdAndUpdate(currentAuthenticateUtente.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        utenteService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @PatchMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> uploadAvatar(
            @AuthenticationPrincipal Utente currentAuthenticatedUser,
            @RequestParam("avatar") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Il file non è stato ricevuto o è vuoto.");
        }
        System.out.println("File ricevuto: " + file.getOriginalFilename());
        return utenteService.uploadAvatar(currentAuthenticatedUser.getId(), file);
    }


}


