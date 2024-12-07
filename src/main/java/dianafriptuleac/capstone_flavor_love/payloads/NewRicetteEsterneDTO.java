package dianafriptuleac.capstone_flavor_love.payloads;

import java.util.List;
import java.util.UUID;

public record NewRicetteEsterneDTO(
        UUID id,
        String title,
        String instructions,
        String image,
        List<IngredientiRicetteEsterneDTO> extendedIngredients
) {
}
