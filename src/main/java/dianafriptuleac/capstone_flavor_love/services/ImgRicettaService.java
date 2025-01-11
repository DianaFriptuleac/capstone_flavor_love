package dianafriptuleac.capstone_flavor_love.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dianafriptuleac.capstone_flavor_love.entities.ImgRicetta;
import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.repositories.ImgRicettaRepository;
import dianafriptuleac.capstone_flavor_love.repositories.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class ImgRicettaService {

    @Autowired
    private ImgRicettaRepository imgRicettaRepository;

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private Cloudinary cloudinary;

    public ImgRicetta addImg(UUID ricettaId, MultipartFile file, Utente currentUser) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new IllegalArgumentException("Ricetta non trovata con ID: " + ricettaId));

        if (!ricetta.getUtente().getId().equals(currentUser.getId()) && !isAdmin(currentUser)) {
            throw new UnauthorizedException("Non hai i permessi per aggiungere un'immagine a questa ricetta.");
        }

        String url;
        try {
            // Upload dell'immagine su Cloudinary
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Errore durante l'upload dell'immagine.");
        }

        // Creazione dell'oggetto ImgRicetta
        ImgRicetta imgRicetta = new ImgRicetta(ricetta, url);

        // Salvo l'img e aggioro ricetta
        imgRicetta = imgRicettaRepository.save(imgRicetta); // Salvo img del db
        ricetta.getImg().add(imgRicetta); // Aggiungo img alla lista della ricetta
        ricettaRepository.save(ricetta); // Salvo ricetta aggiornata

        return imgRicetta; // Ritorno l'oggetto immagine
    }

    private boolean isAdmin(Utente utente) {
        return utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }


    public Page<ImgRicetta> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.imgRicettaRepository.findAll(pageable);
    }


    //cerco img per ricetta
    public Page<ImgRicetta> findByRicettaId(UUID ricettaId, int page, int size, String sortBy) {
        return imgRicettaRepository.findByRicettaId(ricettaId, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    //cancello img
    public void deleteImg(UUID imgId, UUID utenteId, boolean isAdmin) {
        ImgRicetta imgRicetta = imgRicettaRepository.findById(imgId).orElseThrow(() ->
                new NotFoundException("Immagine con id " + imgId + " non trovata!"));

        Ricetta ricetta = imgRicetta.getRicetta();
        if (!ricetta.getUtente().getId().equals(utenteId) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questa immagine!");
        }

        imgRicettaRepository.delete(imgRicetta);
    }

}
