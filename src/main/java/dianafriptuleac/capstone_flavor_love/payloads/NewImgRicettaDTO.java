package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewImgRicettaDTO(

        @NotNull(message = "L'id della ricetta è obbligatorio!")
        UUID ricettaId,

        @NotEmpty(message = "L'url dell'immagine è obbbligatorio!")
        String url
) {
}
