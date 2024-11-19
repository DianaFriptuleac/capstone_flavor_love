package dianafriptuleac.capstone_flavor_love.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteLoginDTO(
        @NotEmpty(message = "L'email è un campo obbligatorio!")
        @Email(message = "L'email inserita non è un'email valida")
        String email,

        @NotEmpty(message = "La password è un campo obbligatorio!")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri!")
        String password) {
}
