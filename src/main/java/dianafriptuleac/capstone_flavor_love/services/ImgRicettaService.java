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

    public String addImg(UUID ricettaId, MultipartFile file, Utente currentUser) {
        Ricetta ricetta = ricettaRepository.findById(ricettaId)
                .orElseThrow(() -> new IllegalArgumentException("Ricetta non trovata con ID: " + ricettaId));

        if (!ricetta.getId().equals(currentUser.getId()) && !isAdmin(currentUser)) {
            throw new UnauthorizedException("Non hai i permessi per aggiungere un'immagine a questa ricetta.");
        }

        String url;
        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Errore durante l'upload dell'immagine.");
        }

        ImgRicetta imgRicetta = new ImgRicetta(ricetta, url);
        ricetta.getImg().add(imgRicetta);
        ricettaRepository.save(ricetta);

        return url;
    }

    public void deleteImg(UUID imgId, UUID utenteId, boolean isAdmin) {
        ImgRicetta imgRicetta = imgRicettaRepository.findById(imgId).orElseThrow(() ->
                new NotFoundException("Immagine con id " + imgId + " non trovata!"));
        Ricetta ricetta = imgRicetta.getRicetta();
        if (!ricetta.getId().equals(utenteId) && !isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per eliminare  l'immagiine!");
        }
        imgRicettaRepository.delete(imgRicetta);
    }

    public boolean ricettaHasImg(UUID id) {
        Ricetta ricetta = ricettaRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Ricetta non trovata con ID: " + id));
        return !ricetta.getImg().isEmpty();
    }

    private boolean isAdmin(Utente utente) {
        return utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }
}
