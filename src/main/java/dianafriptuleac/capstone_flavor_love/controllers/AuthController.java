package dianafriptuleac.capstone_flavor_love.controllers;

import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.payloads.NewUtenteDTO;
import dianafriptuleac.capstone_flavor_love.payloads.UtenteLoginDTO;
import dianafriptuleac.capstone_flavor_love.payloads.UtenteLoginResponseDTO;
import dianafriptuleac.capstone_flavor_love.services.AuthService;
import dianafriptuleac.capstone_flavor_love.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated NewUtenteDTO body, BindingResult validatioResult) {

        if (validatioResult.hasErrors()) {
            String msg = validatioResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload!" + msg);
        }
        return this.utenteService.save(body);

    }

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO body) {
        return this.authService.checkAllCredentialsAndToken(body);
    }
}
