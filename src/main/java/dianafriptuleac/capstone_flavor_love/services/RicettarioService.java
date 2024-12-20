package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.Ricetta;
import dianafriptuleac.capstone_flavor_love.entities.Ricettario;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicettarioDTO;
import dianafriptuleac.capstone_flavor_love.payloads.RispostaRicettaDTO;
import dianafriptuleac.capstone_flavor_love.payloads.RispostaRicettarioDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RicettarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RicettarioService {

    @Autowired
    private RicettarioRepository ricettarioRepository;

    @Autowired
    private RicettaService ricettaService;

    //frindById Ricettario
    public Ricettario findById(UUID ricettarioId, Utente utente, boolean isAdmin) {
        Ricettario ricettario = ricettarioRepository.findById(ricettarioId).orElseThrow(() ->
                new NotFoundException("Il ricettario con l'id " + ricettarioId + "non è stato trovato!"));

        if (!ricettario.getUtente().getId().equals(utente.getId()) && !isAdmin) {
            throw new UnauthorizedException("Non sei autorizzato a modificare questo ricettario");
        }
        return ricettario;
    }

    //get ricettario con le ricette
    public RispostaRicettarioDTO getRicettarioConRicette(UUID ricettarioId, Utente utente, Pageable pageable) {
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        Ricettario ricettario = findById(ricettarioId, utente, isAdmin);
        Page<RispostaRicettaDTO> ricettePage = ricettaService.getRicetteByRicettarioId(ricettarioId, pageable);
        return new RispostaRicettarioDTO(
                ricettario.getId(),
                ricettario.getNome(),
                ricettePage
        );
    }


    //Creo il ricettario
    public Ricettario saveRicettario(NewRicettarioDTO newRicettarioDTO, Utente utente) {
        Ricettario ricettario = new Ricettario(newRicettarioDTO.nome(), utente);

        //Controllo se l'utente ha gia un ricettario con lo stesso nome
        boolean existRicettario = ricettarioRepository.findByUtenteId(utente.getId(), Pageable.unpaged()).
                stream().anyMatch(ricet -> ricet.getNome().equalsIgnoreCase(newRicettarioDTO.nome()));
        if (existRicettario) {
            throw new BadRequestException("Hai già un ricettario con questo nome!");
        }

        return ricettarioRepository.save(ricettario);
    }

    //Get Ricettari
    public Page<Ricettario> getRicettariPerUtente(Utente utente, Pageable pageable) {

        Page<Ricettario> ricettari = ricettarioRepository.findByUtenteId(utente.getId(), pageable);
        if (ricettari.isEmpty()) {
            throw new NotFoundException("Nessun ricettario trovato!");
        }
        return ricettari;
    }

    //Aggiungo una ricetta al ricettario
    public Ricettario addRicettaToRicettario(UUID ricettarioId, UUID ricettaId, Utente utente) {
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        Ricettario ricettario = findById(ricettarioId, utente, isAdmin);
        Ricetta ricetta = ricettaService.findById(ricettaId);
        if (ricettario.getRicette().contains(ricetta)) {
            throw new BadRequestException("La ricetta è già presente nel ricettario");
        }
        ricettario.addRicetta(ricetta);
        return ricettarioRepository.save(ricettario);
    }

    //Cerco ricettario per nome
    public Ricettario findByNome(String nome, Utente utente) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BadRequestException("Il nome del ricettario non può essere nullo o vuoto");
        }

        return ricettarioRepository.findByNomeContainingIgnoreCaseAndUtenteId(nome.trim(), utente.getId()).orElseThrow(() ->
                new NotFoundException(("Il ricettario con il nome: " + nome + " non è stato trovato!")));
    }

    //Cancello una ricetta dal ricettario
    public Ricettario removeRicettaFromRicettario(UUID ricettarioId, UUID ricettaId, Utente utente) {
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        Ricettario ricettario = findById(ricettarioId, utente, isAdmin);
        Ricetta ricetta = ricettaService.findById(ricettaId);

        if (!ricettario.getRicette().contains(ricetta)) {
            throw new NotFoundException("La ricetta non è presente nel ricettario");
        }
        ricettario.removeRicetta(ricetta);
        return ricettarioRepository.save(ricettario);
    }


    //Cancello intero ricettario
    public void deleteRicettario(UUID ricettarioId, Utente utente) {
        boolean isAdmin = utente.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        Ricettario ricettario = findById(ricettarioId, utente, isAdmin);
        ricettarioRepository.delete(ricettario);
    }
}

