package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewRicettarioDTO(
        @NotEmpty(message = "Inserisci un nome al tuo ricettario")
        @Size(min = 3, max = 50, message = "Il nome deve essere tra 3 e 50 caratteri")
        String nome
) {
}
