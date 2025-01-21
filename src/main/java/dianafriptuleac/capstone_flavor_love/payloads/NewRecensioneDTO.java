package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.*;

public record NewRecensioneDTO(
        @NotNull(message = "Il numero delle stelle è obbligatorio!")
        @Min(value = 1, message = "Il numero minimo di stelle deve essere almeno 1.")
        @Max(value = 5, message = "Il numero massimo delle stelle non può superare 5.")
        double stelle,

        @NotEmpty(message = "Inserire un commeno!")
        @Size(max = 5000, message = "Il commento non deve superare 5000 caratteri.")
        String commento

) {
}
