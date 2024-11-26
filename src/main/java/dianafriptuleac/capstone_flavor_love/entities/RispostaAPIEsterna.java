package dianafriptuleac.capstone_flavor_love.entities;

import dianafriptuleac.capstone_flavor_love.payloads.NewRicetteEsterneDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RispostaAPIEsterna {
    private List<NewRicetteEsterneDTO> results;
}
