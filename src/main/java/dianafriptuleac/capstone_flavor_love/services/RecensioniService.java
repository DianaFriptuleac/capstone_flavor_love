package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.Recensioni;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewRecensioneDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RecensioniRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RecensioniService {
    @Autowired
    private RecensioniRepository recensioniRepository;

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private UtenteService utenteService;

    //scrivo recensione per ricetta
    @Transactional
    public Recensioni saveRecensione(UUID ricettaId, NewRecensioneDTO newRecensioneDTO, Utente utente) {
        Ricetta ricetta = ricettaService.findById(ricettaId);

        Recensioni recensioni = new Recensioni();
        recensioni.setStelle(newRecensioneDTO.stelle());
        recensioni.setCommento(newRecensioneDTO.commento());
        recensioni.setDataCreazione(LocalDateTime.now());
        recensioni.setUtente(utente);
        recensioni.setRicetta(ricetta);

        return recensioniRepository.save(recensioni);
    }

    //cancello la recensione
    @Transactional
    public void deleteRecensione(UUID recensioneId, Utente utente, String adminRole) {
        Recensioni recensioni = recensioniRepository.findById(recensioneId).orElseThrow(() ->
                new NotFoundException("recensione con ID " + recensioneId + " non è stata trovata"));
        boolean isAdmin = utente.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(adminRole));
        if (!recensioni.getUtente().getId().equals(utente.getId()) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per cancellare questa recensione!");
        }
        recensioniRepository.delete(recensioni);
    }

    //cancello tutte le recensioni associate a una ricetta
    @Transactional
    public void deleteRecensioniByRicetta(UUID ricettaId) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        List<Recensioni> recensioni = ricetta.getRecensioni();
        recensioniRepository.deleteAll(recensioni);
    }

    //modifica recensione
    @Transactional
    public Recensioni updateRecensione(UUID recensioneId, NewRecensioneDTO newRecensioneDTO, Utente utente, String adminRole) {
        Recensioni recensioni = recensioniRepository.findById(recensioneId).orElseThrow(() ->
                new NotFoundException("Recensione con ID " + recensioneId + " non è stata trovata"));
        //controllo se l'utente e il creatore o admin
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(adminRole));

        if (!recensioni.getUtente().getId().equals(utente.getId()) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per modificare questa recensione!");
        }

        //aggiorno la recensione
        recensioni.setStelle(newRecensioneDTO.stelle());
        recensioni.setCommento(newRecensioneDTO.commento());

        return recensioniRepository.save(recensioni);
    }

    //cerco tutte le recensioni della ricetta
    @Transactional
    public Page<Recensioni> getRecensioniByRicetta(UUID ricettaId, Pageable pageable) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        Page<Recensioni> recensioniPage = recensioniRepository.findByRicetta(ricetta, pageable);

        if (recensioniPage.isEmpty()) {
            throw new NotFoundException("Nessuna recensione trovata per la ricetta con ID " + ricettaId);
        }

        return recensioniPage;
    }

    //filtro recensioni per stelle
    @Transactional
    public Page<Recensioni> getRecensioniByRicettaAndStelle(UUID ricettaId, double stelle, Pageable pageable) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        Page<Recensioni> recensioniPage = recensioniRepository.findByRicettaAndStelle(ricetta, stelle, pageable);

        if (recensioniPage.isEmpty()) {
            throw new NotFoundException("Nessuna recensione trovata per la ricetta con ID " + ricettaId);
        }

        return recensioniPage;

    }
}
