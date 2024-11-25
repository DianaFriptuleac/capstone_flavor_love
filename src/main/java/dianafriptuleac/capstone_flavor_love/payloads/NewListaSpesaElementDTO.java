package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewListaSpesaElementDTO(
        @NotEmpty(message = "Il nome dell'ingrediente è obbligatorio!")
        @Size(min = 1, max = 100, message = "Il nome dell'ingrediente deve essere compreso tra 1 e 100 caratteri")
        String nome,

        @NotEmpty(message = "Il dosaggio è obbligatorio!")
        String dosaggio,

        String sezione,
        int quantita

) {
}
