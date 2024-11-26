package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.RicetteEsterne;
import dianafriptuleac.capstone_flavor_love.entities.RispostaAPIEsterna;
import dianafriptuleac.capstone_flavor_love.payloads.DettagliRicettaEsternaDTO;
import dianafriptuleac.capstone_flavor_love.payloads.NewRicetteEsterneDTO;
import dianafriptuleac.capstone_flavor_love.repositories.RicetteEsterneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RicetteEsterneService {

    @Value("${myapp.jwt.token}")
    private String jwtToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RicetteEsterneRepository ricetteEsterneRepository;

    public List<RicetteEsterne> fetchAndSaveAllRicette(String query) {
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=" + jwtToken;

        System.out.println("URL chiamato: " + url);

        ResponseEntity<RispostaAPIEsterna> risposta = restTemplate.getForEntity(url, RispostaAPIEsterna.class);
        RispostaAPIEsterna response = risposta.getBody();

        if (response != null && response.getResults() != null) {
            List<RicetteEsterne> salvate = new ArrayList<>();
            for (NewRicetteEsterneDTO result : response.getResults()) {
                RicetteEsterne ricetta = new RicetteEsterne(result.title(), "", result.image());
                salvate.add(ricetteEsterneRepository.save(ricetta));
            }
            return salvate;
        }

        return Collections.emptyList();
    }

    public DettagliRicettaEsternaDTO getDettagliRicetta(Long id) {
        String url = "https://api.spoonacular.com/recipes/" + id + "/information?apiKey=" + jwtToken;

        System.out.println("Chiamata dettagli: " + url);

        ResponseEntity<DettagliRicettaEsternaDTO> risposta = restTemplate.getForEntity(url, DettagliRicettaEsternaDTO.class);
        return risposta.getBody();
    }

    public Page<RicetteEsterne> getAllRicetteSalvate(Pageable pageable) {
        return ricetteEsterneRepository.findAll(pageable);
    }
}