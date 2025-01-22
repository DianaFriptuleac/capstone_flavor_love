package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewRistoranteDTO(
        @NotEmpty(message = "Il nome del ristorante è obbligatorio!")
        @Size(min = 1, max = 150, message = "Il nome deve essere compreso tra 1 e 150 caratteri!")
        String nome,

        @NotEmpty(message = "L'indirizzo del ristorante è obbligatorio!")
        @Size(min = 2, max = 300, message = "L'indirizzo deve essere compreso tra 2 e 300 caratteri!")
        String indirizzo,

        @NotEmpty(message = "La città è obbligatoria!")
        @Size(min = 1, max = 200, message = "La città deve essere compresa tra 1 e 200 caratteri!")
        String citta,

        @NotNull(message = "La latitudine è obbligatoria!")
        double latitudine,

        @NotNull(message = "La longitudine è obbligatoria!")
        double longitudine,

        @NotEmpty(message = "La categoria è obbligatoria!")
        @Size(min = 1, max = 100, message = "La categoria deve essere compresa tra 1 e 100 caratteri!")
        String categoria,

        @NotEmpty(message = "Il numero di telefono è obbligatorio!")
        @Size(min = 3, max = 30, message = "Il numero di telefono deve essere compreso tra 3 e 30 caratteri!")
        String telefono,

        @NotEmpty(message = "Il link del sito web è obbligatorio!")
        @Size(max = 500, message = "Il link deve essere al massimo di 500 caratteri!")
        String link,

        String immagine
) {
}
