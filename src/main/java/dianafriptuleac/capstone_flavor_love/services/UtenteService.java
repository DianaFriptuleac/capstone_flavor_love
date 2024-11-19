package dianafriptuleac.capstone_flavor_love.services;

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

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

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
                .orElseThrow(() -> new NotFoundException(String.valueOf(utenteId)));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

}
