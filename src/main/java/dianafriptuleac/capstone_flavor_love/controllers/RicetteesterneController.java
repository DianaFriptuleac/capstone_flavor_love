package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.RicetteEsterne;
import dianafriptuleac.capstone_flavor_love.payloads.DettagliRicettaEsternaDTO;
import dianafriptuleac.capstone_flavor_love.services.RicetteEsterneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ricetteEsterne")
public class RicetteesterneController {

    @Autowired
    private RicetteEsterneService ricetteEsterneService;

    @GetMapping("/fetchAll")
    public List<RicetteEsterne> fetchAndSaveAllRicette(@RequestParam String query) {
        return ricetteEsterneService.fetchAndSaveAllRicette(query);
    }

    @GetMapping("/dettagli")
    public DettagliRicettaEsternaDTO getDettagliRicetta(@RequestParam Long id) {
        return ricetteEsterneService.getDettagliRicetta(id);
    }

    @GetMapping("/allRicette")
    public Page<RicetteEsterne> getAllRicette(Pageable pageable) {
        return ricetteEsterneService.getAllRicetteSalvate(pageable);
    }
}
