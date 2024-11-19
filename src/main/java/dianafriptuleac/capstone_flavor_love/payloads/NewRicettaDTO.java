package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record NewRicettaDTO(
        @NotEmpty(message = "Il titolo della ricetta è obbligatorio!")
        @Size(min = 3, max = 40, message = "Il titolo deve essere compreso tra 3 e 40 caratteri!")
        String titolo,

        @NotEmpty(message = "Il procedimento è obbligatorio!")
        @Size(max = 8000, message = "Il procedimento della ricetta deve contenere al massimo 8000 caratteri.")
        String procedimento,

        @NotEmpty(message = "Specifica almeno una categoria!")
        List<UUID> categorieId

) {
}
