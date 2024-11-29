package dianafriptuleac.capstone_flavor_love.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        System.out.println("Richiesta ricevuta:");
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("Content-Type: " + httpRequest.getContentType());
        System.out.println("URL: " + httpRequest.getRequestURL());

        if (httpRequest.getContentType() != null && httpRequest.getContentType().contains("multipart/form-data")) {
            System.out.println("La richiesta contiene multipart/form-data.");
        } else {
            System.out.println("Errore: Content-Type non corretto -> " + httpRequest.getContentType());
        }

        chain.doFilter(request, response);
    }
}
