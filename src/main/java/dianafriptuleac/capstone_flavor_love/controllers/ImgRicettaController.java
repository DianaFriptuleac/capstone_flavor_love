package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.ImgRicetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.services.ImgRicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/imgRicette")
public class ImgRicettaController {

    @Autowired
    ImgRicettaService imgRicettaService;

    @PostMapping("/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public String uploadImg(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                            @PathVariable UUID ricettaId,
                            @RequestParam("file") MultipartFile file) {
        return imgRicettaService.addImg(ricettaId, file, currentAuthenticatedUser);
    }


    @DeleteMapping("/{imgId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImg(@AuthenticationPrincipal Utente currentAuthenticatedUser,
                          @PathVariable UUID imgId) {
        boolean isAdmin = currentAuthenticatedUser.getAuthorities().stream()

                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        imgRicettaService.deleteImg(imgId, currentAuthenticatedUser.getId(), isAdmin);
    }

    @GetMapping
    public Page<ImgRicetta> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "8") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.imgRicettaService.findAll(page, size, sortBy);
    }

    //get img in base alla ricetta
    @GetMapping("/ricetta/{ricettaId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Page<ImgRicetta> getImagesByRicettaId(
            @PathVariable UUID ricettaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return imgRicettaService.findByRicettaId(ricettaId, page, size, sortBy);
    }

}
