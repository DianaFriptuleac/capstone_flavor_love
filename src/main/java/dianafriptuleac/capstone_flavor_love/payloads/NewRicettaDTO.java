package dianafriptuleac.capstone_flavor_love.payloads;

import dianafriptuleac.capstone_flavor_love.enums.CostoRicetta;
import dianafriptuleac.capstone_flavor_love.enums.DifficoltaRicetta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewRicettaDTO(
        @NotEmpty(message = "Il titolo della ricetta è obbligatorio!")
        @Size(min = 3, max = 60, message = "Il titolo deve essere compreso tra 3 e 60 caratteri!")
        String titolo,

        @NotEmpty(message = "Il procedimento è obbligatorio!")
        @Size(max = 10000, message = "Il procedimento della ricetta deve contenere al massimo 10000 caratteri.")
        String procedimento,

        @NotNull(message = "La difficoltà è obbligatoria!")
        DifficoltaRicetta difficoltaRicetta,

        @Min(value = 1, message = "Il tempo di preparazione deve essere almeno 1 minuto.")
        int tempoPreparazioneMinuti,

        int tempoCotturaMinuti,

        @NotNull(message = "Il costo è obbligatorio!")
        CostoRicetta costoRicetta,

        @NotEmpty(message = "Specifica almeno una categoria!")
        List<String> nomeCategorieRicette

) {
}
