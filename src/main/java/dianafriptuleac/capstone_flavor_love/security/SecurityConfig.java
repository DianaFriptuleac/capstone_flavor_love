package dianafriptuleac.capstone_flavor_love.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  /*  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());

        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

      / httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.GET, "/api/ricetteEsterne/allRicette").permitAll() // Rende pubblico l'endpoint allRicette
                        .requestMatchers(HttpMethod.GET, "/api/ricetteEsterne/fetchAll").permitAll()  // Endpoint fetchAll pubblico
                        .requestMatchers(HttpMethod.DELETE, "/api/ricetteEsterne/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/ricette").hasAnyAuthority("USER", "ADMIN") // Endpoint protetto// DELETE protetto solo per ADMIN
                        .requestMatchers("/api/**").authenticated() // Protegge gli altri endpoint
                        .anyRequest().permitAll()); // Accesso a tutto il resto

        httpSecurity.cors(Customizer.withDefaults());

        return httpSecurity.build();

        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.requestMatchers("/**").permitAll());


        return httpSecurity.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Per poter configurare tutto ciò che è relativo alla sicurezza devo configurare Spring Security tramite questo apposito bean, il quale
        // mi consentirà di:
        // - disabilitare comportamenti di default che non ci interessano
        httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable()); // Non voglio il form di login (avremo React per quello)
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()); // Non voglio la protezione da CSRF (perché non ci serve
        // ed inoltre mi complicherebbe anche il lato FE)
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Non vogliamo utilizzare le Sessioni (perché JWT NON utilizza le sessioni)
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.requestMatchers("/**").permitAll()); // Disabilitiamo il 401 che riceviamo di default
        // per OGNI richiesta che facciamo su OGNI endpoint
        // - personalizzare il comportamento di alcune funzionalità preesistenti
        // - aggiungere filtri personalizzati alla Filter Chain
        httpSecurity.cors(Customizer.withDefaults()); // <-- OBBLIGATORIA SE VOGLIAMO CONFIGURARE I CORS GLOBALMENTE CON UN BEAN
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder getBCrypt() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}