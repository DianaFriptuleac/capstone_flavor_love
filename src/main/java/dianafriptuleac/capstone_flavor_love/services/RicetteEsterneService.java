package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.IngredientiRicettaEsterna;
import dianafriptuleac.capstone_flavor_love.entities.RicetteEsterne;
import dianafriptuleac.capstone_flavor_love.entities.RispostaAPIEsterna;
import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.NotFoundException;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicetteEsterneDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RicetteEsterneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class RicetteEsterneService {

    @Value("${myapp.jwt.token}")
    private String jwtToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RicetteEsterneRepository ricetteEsterneRepository;

    public Page<RicetteEsterne> fetchAndSaveAllRicette(String query, Pageable pageable) {
        String searchUrl = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=" + jwtToken;

        System.out.println("URL chiamato: " + searchUrl);

        // Chiamata API Spoonacular
        ResponseEntity<RispostaAPIEsterna> risposta = restTemplate.getForEntity(searchUrl, RispostaAPIEsterna.class);
        RispostaAPIEsterna response = risposta.getBody();

        if (response != null && response.getResults() != null) {
            for (NewRicetteEsterneDTO result : response.getResults()) {
                String dettagliUrl = "https://api.spoonacular.com/recipes/" + result.id() + "/information?apiKey=" + jwtToken;

                System.out.println("URL dettagli: " + dettagliUrl);
                ResponseEntity<NewRicetteEsterneDTO> dettagliRisposta = restTemplate.getForEntity(dettagliUrl, NewRicetteEsterneDTO.class);
                NewRicetteEsterneDTO dettagli = dettagliRisposta.getBody();

                if (dettagli != null) {
                    RicetteEsterne ricetta = new RicetteEsterne(
                            dettagli.title(),
                            dettagli.instructions() != null ? dettagli.instructions() : "Preparazione non disponibile",
                            dettagli.image()
                    );

                    // Salvo gli ingredienti associati
                    if (dettagli.extendedIngredients() != null) {
                        List<IngredientiRicettaEsterna> ingredienti = dettagli.extendedIngredients().stream()
                                .map(ing -> new IngredientiRicettaEsterna(
                                        ing.name(),
                                        Double.parseDouble(ing.amount()),
                                        ing.unit(),
                                        ricetta
                                ))
                                .toList();
                        ricetta.setIngredienti(ingredienti);
                    }

                    ricetteEsterneRepository.save(ricetta);
                }
            }
            return ricetteEsterneRepository.findAll(pageable);
        }

        return Page.empty(pageable);
    }

    public Page<RicetteEsterne> getAllRicetteSalvate(Pageable pageable) {
        return ricetteEsterneRepository.findAll(pageable);
    }

    //Cancello ricetta
    public void removeRicetta(UUID id, Utente currentAuthenticatedUser, String adminRole) {
        RicetteEsterne ricetteEsterne = ricetteEsterneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ricetta con ID " + id + " non trovata!"));

        // Controllo se l'utente è admin o creatore
        boolean isAdmin = currentAuthenticatedUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(adminRole));

        if (!isAdmin) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questa ricetta!");

        }
        ricetteEsterneRepository.delete(ricetteEsterne);
    }

    //cerco per titolo
    public Page<RicetteEsterne> cercaByTitolo(String query, Pageable pageable) {
        return ricetteEsterneRepository.searchByTitle(query, pageable);
    }
}
