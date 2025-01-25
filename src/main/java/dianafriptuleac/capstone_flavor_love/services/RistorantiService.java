package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.Ristoranti;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewRistoranteDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RistoranteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RistorantiService {

    @Autowired
    private RistoranteRepository ristoranteRepository;

    //Crea ristorante
    @Transactional
    public Ristoranti saveRistorante(NewRistoranteDTO newRistoranteDTO, Utente utente) {
        //controllo se esiste gia un ristorante con questo nome e indirizzo
        boolean ristoranteExist = ristoranteRepository.findByNomeAndIndirizzo(
                newRistoranteDTO.nome(), newRistoranteDTO.indirizzo()
        ).isPresent();
        if (ristoranteExist) {
            throw new BadRequestException("Esiste già un ristorante con questo nome a questo indirizzo!");
        }
        //creo il nuovo ristorante
        Ristoranti ristoranti = new Ristoranti(
                newRistoranteDTO.nome(),
                newRistoranteDTO.indirizzo(),
                newRistoranteDTO.citta(),
                newRistoranteDTO.latitudine(),
                newRistoranteDTO.longitudine(),
                newRistoranteDTO.categorie(),
                newRistoranteDTO.telefono(),
                newRistoranteDTO.link(),
                newRistoranteDTO.immagine(),
                utente
        );
        return ristoranteRepository.save(ristoranti);
    }

    //Modifica campi ristorante
    @Transactional
    public Ristoranti updateRistorante(UUID ristoranteId, NewRistoranteDTO newRistoranteDTO, Utente utente, String adminRole) {
        Ristoranti ristorante = ristoranteRepository.findById(ristoranteId).orElseThrow(() ->
                new NotFoundException("Il ristorante con id " + ristoranteId + " non è stato trovato!"));

        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(adminRole));

        if (!isAdmin && !ristorante.getUtente().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo ristorante!");
        }

        ristorante.setNome(newRistoranteDTO.nome());
        ristorante.setIndirizzo(newRistoranteDTO.indirizzo());
        ristorante.setCitta(newRistoranteDTO.citta());
        ristorante.setLatitudine(newRistoranteDTO.latitudine());
        ristorante.setLongitudine(newRistoranteDTO.longitudine());
        ristorante.setCategorie(newRistoranteDTO.categorie());
        ristorante.setTelefono(newRistoranteDTO.telefono());
        ristorante.setLink(newRistoranteDTO.link());
        ristorante.setImmagine(newRistoranteDTO.immagine());

        return ristoranteRepository.save(ristorante);
    }

    //cancello ristorante
    @Transactional
    public void deleteRistorante(UUID ristoranteId, Utente utente, String adminRole) {
        Ristoranti ristoranti = ristoranteRepository.findById(ristoranteId).orElseThrow(() ->
                new NotFoundException("Il ristorante con id " + ristoranteId + " non è stato trovatoo!"));

        boolean isAdmin = utente.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(adminRole));

        if (!isAdmin && !ristoranti.getUtente().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non hai i permessi per cancellare questo ristorante!");
        }
        ristoranteRepository.delete(ristoranti);
    }

    //get tutti i ristoranti
    public Page<Ristoranti> getAllRistoranti(Pageable pageable) {
        return ristoranteRepository.findAll(pageable);
    }

    //cerco per nome, citta, categorie
    public Page<Ristoranti> searchRistoranti(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new NotFoundException("Inserire una keyword valida");
        }
        return ristoranteRepository.findByNomeOrCittaOrCategorie(keyword, pageable);
    }
}
