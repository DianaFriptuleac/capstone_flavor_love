package dianafriptuleac.capstone_flavor_love.payloads;

import java.util.List;

public record DettagliRicettaEsternaDTO(
        String summary,
        List<IngredientsRicetteEsterneDTO> ingredients,
        String instructions
) {
}
