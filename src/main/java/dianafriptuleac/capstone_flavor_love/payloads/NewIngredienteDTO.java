package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewIngredienteDTO(
        @NotEmpty(message = "Il nome dell'ingrediente è obbligatorio!")
        @Size(min = 1, max = 100, message = "Il nome dell'ingrediente deve essere compreso tra 1 e 100 caratteri")
        String nome,

        @NotEmpty(message = "Il dosaggio è obbligatorio!")
        @Size(max = 50, message = "Il dosaggio non puà superare i 50 caratteri")
        String dosaggio,

        String sezione

) {
}
