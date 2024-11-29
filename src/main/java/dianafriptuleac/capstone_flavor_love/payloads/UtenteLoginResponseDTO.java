package dianafriptuleac.capstone_flavor_love.payloads;

import java.util.UUID;

public record UtenteLoginResponseDTO(String accessToken,
                                     UUID id, String nome, String cognome, String email, String avatar) {
}
