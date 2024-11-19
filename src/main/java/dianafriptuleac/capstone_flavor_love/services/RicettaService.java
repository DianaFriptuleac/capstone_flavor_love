package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.CategoriaRicetta;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettaDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RicettaService {

    @Autowired
    RicettaRepository ricettaRepository;

    @Autowired
    CategorieRicettaService categorieRicettaService;

    public Ricetta saveRicetta(NewRicettaDTO newRicettaDTO, UUID utenteId) {
        Ricetta ricetta = new Ricetta(newRicettaDTO.titolo(), newRicettaDTO.procedimento());
        List<CategoriaRicetta> categorie = newRicettaDTO.nomeCategorieRicette().stream()
                .map(nome -> categorieRicettaService.findByNome(nome))
                .toList();
        ricetta.setCategorie(categorie);
        return ricettaRepository.save(ricetta);
    }


    public Ricetta updateRicetta(UUID ricettaId, NewRicettaDTO newRicettaDTO, UUID utenteId) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException("Ricetta con ID " + ricettaId + " non trovata!"));

        if (!ricetta.getId().equals(utenteId)) {
            throw new UnauthorizedException("Non hai i permessi per modificare questa ricetta!");
        }

        ricetta.setTitolo(newRicettaDTO.titolo());
        ricetta.setProcedimento(newRicettaDTO.procedimento());

        List<CategoriaRicetta> categorie = newRicettaDTO.nomeCategorieRicette().stream()
                .map(nome -> categorieRicettaService.findByNome(nome))
                .toList();

        ricetta.setCategorie(categorie);
        return ricettaRepository.save(ricetta);
    }


    public void deleteRicetta(UUID ricettaId, UUID utenteId, boolean isAdmin) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new NotFoundException("Ricetta con ID " + ricettaId + " non trovata!"));
        if (!ricetta.getId().equals(utenteId) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questa ricetta!");
        }
        ricettaRepository.delete(ricetta);
    }

    public Page<Ricetta> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ricettaRepository.findAll(pageable);
    }


    public Page<Ricetta> findByName(String titolo, int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ricettaRepository.findByTitoloContainingIgnoreCase(titolo, pageable);
    }

}
