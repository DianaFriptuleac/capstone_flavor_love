package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewCategoriaRicettaDTO(
        @NotEmpty(message = "Il nome è obbligatorio!")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri!")
        String nome,
        @Size(max = 300, message = "La descrizione della categoria deve contenere al massimo 300 caratteri!")
        String descrizione
) {
}
