package dianafriptuleac.capstone_flavor_love.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.payloads.NewUtenteDTO;
import dianafriptuleac.capstone_flavor_love.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinary;

    public Utente save(NewUtenteDTO body) {
        this.utenteRepository.findByEmail(body.email()).ifPresent(
                utente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );
        Utente newUtente = new Utente(body.nome(), body.cognome(), body.email(),
                bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.utenteRepository.save(newUtente);
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + utenteId + " non trovato"));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

    public Utente findByIdAndUpdate(UUID utenteId, NewUtenteDTO body) {
        Utente foundUtente = this.findById(utenteId);
        if (!foundUtente.getEmail().equals(body.email())) {
            this.utenteRepository.findByEmail(body.email()).ifPresent(
                    utente -> {
                        throw new BadRequestException("Email " + body.email() + " è già in uso!");
                    }
            );
        }
        foundUtente.setNome(body.nome());
        foundUtente.setCognome(body.cognome());
        foundUtente.setEmail(body.email());
        foundUtente.setPassword(bcrypt.encode(body.password()));
        return this.utenteRepository.save(foundUtente);
    }


    public void findByIdAndDelete(UUID utenteId) {
        Utente foundUtente = this.findById(utenteId);
        this.utenteRepository.delete(foundUtente);
    }

    public String uploadAvatar(UUID utenteId, MultipartFile file) {
        Utente utente = findById(utenteId);
        String url;

        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            utente.setAvatar(url);
            utenteRepository.save(utente);
        } catch (IOException e) {
            throw new BadRequestException("Errore durante l'upload dell'immagine.");
        }

        return url;
    }

}
