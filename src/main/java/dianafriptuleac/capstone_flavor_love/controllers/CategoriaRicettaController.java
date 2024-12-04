package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.CategoriaRicetta;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.payloads.NewCategoriaRicettaDTO;
import dianafriptuleac.capstone_flavor_love.services.CategorieRicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorie")
public class CategoriaRicettaController {

    @Autowired
    private CategorieRicettaService categorieRicettaService;

    @PostMapping("/createCategory")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaRicetta creaCategoria(@RequestBody @Validated NewCategoriaRicettaDTO body,
                                          BindingResult validatioResult) {

        if (validatioResult.hasErrors()) {
            String msg = validatioResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload!" + msg);
        }
        return this.categorieRicettaService.creaCategoria(body);

    }


    @GetMapping
    public Page<CategoriaRicetta> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "8") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return this.categorieRicettaService.findAll(page, size, sortBy);
    }

    @GetMapping("/{nome}")
    public CategoriaRicetta findById(@PathVariable String nome) {
        return this.categorieRicettaService.getCategoriaByNome(nome);
    }

    @DeleteMapping("/{nome}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoria(@PathVariable String nome) {
        categorieRicettaService.deleteCategoria(nome);
    }


}
