package dianafriptuleac.capstone_flavor_love.security;

import dianafriptuleac.capstone_flavor_love.entities.Utente;
import dianafriptuleac.capstone_flavor_love.services.UtenteService;
import dianafriptuleac.capstone_flavor_love.tools.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;
    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        String authHeader = request.getHeader("Authorization");

        // Salta il controllo per endpoint pubblici
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        /*if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");
        String accessToken = authHeader.substring(7);

        jwt.verifyToken(accessToken);

        String utenteId = jwt.getIdFromToken(accessToken);
        Utente currentUser = this.utenteService.findById(UUID.fromString(utenteId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Authorities utente: " + currentUser.getAuthorities());
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/ricetteEsterne/allRicette") || path.startsWith("/auth/") &&
                !path.contains("/ricetteEsterne/");
    }*/
        // Se non c'è Authorization o non è Bearer, NON lanciare eccezione:
        // lascia che Spring Security risponda 401 quando la rotta è protetta
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authHeader.substring(7);
        try {
            jwt.verifyToken(accessToken);
            String utenteId = jwt.getIdFromToken(accessToken);
            Utente currentUser = this.utenteService.findById(UUID.fromString(utenteId));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    currentUser, null, currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // opzionale: restituisci 401 pulito
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Token non valido o mancante\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // Rotte pubbliche / health / error / statiche
        return path.startsWith("/auth/")
                || path.equals("/api/ricetteEsterne/allRicette")
                || path.equals("/")
                || path.startsWith("/health")
                || path.startsWith("/actuator")
                || path.startsWith("/error")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}
