package dianafriptuleac.capstone_flavor_love.payloads;

import org.springframework.data.domain.Page;

import java.util.UUID;

public record RispostaRicettarioDTO(
        UUID id,
        String nome,
        Page<RispostaRicettaDTO> ricetta
) {
}
