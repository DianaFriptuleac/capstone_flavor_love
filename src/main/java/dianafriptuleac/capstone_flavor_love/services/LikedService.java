package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.Liked;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.repositories.LikedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LikedService {
    @Autowired
    private LikedRepository likedRepository;

    @Autowired
    private RicettaService ricettaService;

    //Ottengo Liked per utente- se non c'e , verra creata
    public Liked getLikedByUtente(Utente utente) {
        return likedRepository.findByUtenteId(utente.getId()).orElseGet(() -> {
            Liked newListaLiked = new Liked();
            newListaLiked.setUtente(utente);
            return likedRepository.save(newListaLiked);
        });
    }

    //Add ricetta alla Liked
    public Liked addRicettaToLiked(UUID ricettaId, Utente utente) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        Liked liked = getLikedByUtente(utente);

        if (!liked.getRicette().contains(ricetta)) {
            liked.addRicetta(ricetta);
            return likedRepository.save(liked);
        } else {
            throw new IllegalArgumentException("La ricetta è già nella lista Liked.");
        }
    }

    //delete ricetta dal liked
    public void removeRicettaFromLiked(UUID ricettaId, Utente utente) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        Liked liked = getLikedByUtente(utente);

        if (liked.getRicette().contains(ricetta)) {
            liked.removeRicetta(ricetta);
            likedRepository.save(liked);
        } else {
            throw new NotFoundException("La ricetta non è nella lista Liked.");
        }
    }

    // elimino ricetta anche dal liked se la ricetta viene eliminata
    @Transactional
    public void deleteByRicetta(Ricetta ricetta) {
        List<Liked> likedList = likedRepository.findAll();
        for (Liked liked : likedList) {
            liked.getRicette().remove(ricetta);
            likedRepository.save(liked);
        }
    }
}
