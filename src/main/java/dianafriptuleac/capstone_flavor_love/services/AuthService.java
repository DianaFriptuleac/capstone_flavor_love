package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.UnauthorizedException;
import dianafriptuleac.capstone_flavor_love.payloads.UtenteLoginDTO;
import dianafriptuleac.capstone_flavor_love.payloads.UtenteLoginResponseDTO;
import dianafriptuleac.capstone_flavor_love.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public UtenteLoginResponseDTO checkAllCredentialsAndToken(UtenteLoginDTO body) {
        Utente utenteFound = this.utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), utenteFound.getPassword())) {
            String accessToken = jwt.createToken(utenteFound);
            return new UtenteLoginResponseDTO(
                    accessToken,
                    utenteFound.getId(),
                    utenteFound.getNome(),
                    utenteFound.getCognome(),
                    utenteFound.getNome(),
                    utenteFound.getAvatar()
            );
        } else {
            throw new UnauthorizedException("Credenziali utente errate!");
        }
    }
}
