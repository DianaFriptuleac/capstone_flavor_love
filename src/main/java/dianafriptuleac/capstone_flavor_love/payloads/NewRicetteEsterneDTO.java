package dianafriptuleac.capstone_flavor_love.payloads;

import java.util.List;

public record NewRicetteEsterneDTO(
        Long id,
        String title,
        String instructions,
        String image,
        List<IngredientiRicetteEsterneDTO> extendedIngredients
) {
}
