package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewListaSpesaDTO(
        @NotEmpty(message = "Il nome dell'utente è obbligatorio!")
        @Size(min = 3, max = 30, message = "Il nome dell'utente deve essere compreso tra 3 e 30 caratteri!")
        String nomeUtente
) {
}
