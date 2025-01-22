package dianafriptuleac.capstone_flavor_love.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ristoranti")
@NoArgsConstructor
@Getter
@Setter
public class Ristoranti {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    private String indirizzo;
    private String citta;
    private double latitudine;
    private double longitudine;
    private String categorie;
    private String telefono;
    private String link;
    private String immagine;

    // relazione utente
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    public Ristoranti(String nome, String indirizzo, String citta,
                      double latitudine, double longitudine, String categorie,
                      String telefono, String link, String immagine, Utente utente) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.categorie = categorie;
        this.telefono = telefono;
        this.link = link;
        this.immagine = immagine;
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Ristoranti{" +
                "nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", citta='" + citta + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", categorie='" + categorie + '\'' +
                ", telefono='" + telefono + '\'' +
                ", link='" + link + '\'' +
                ", immagine='" + immagine + '\'' +
                '}';
    }
}
