package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dianafriptuleac.capstone_flavor_love.enums.CostoRicetta;
import dianafriptuleac.capstone_flavor_love.enums.DifficoltaRicetta;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ricette")
@NoArgsConstructor
@Getter
@Setter
public class Ricetta {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String titolo;

    //Per il procedimento che può essere lungo.
    @Column(length = 8000)
    private String procedimento;

    @Enumerated(EnumType.STRING)
    private DifficoltaRicetta difficoltaRicetta;

    private int tempoPreparazioneMinuti;
    private int tempoCotturaMinuti;

    @Enumerated(EnumType.STRING)
    private CostoRicetta costoRicetta;


    // Categoria della ricetta
    @ManyToMany
    @JoinTable(
            name = "ricetta_categorie",
            joinColumns = @JoinColumn(name = "ricetta_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @ToString.Exclude
    @JsonIgnore
    private List<CategoriaRicetta> categorie = new ArrayList<>();

    // Immagini
    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ImgRicetta> img = new ArrayList<>();


    //Utenti
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;


    //Ingredienti
    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Ingrediente> ingredienti = new ArrayList<>();


    //Lista spesa
    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<ListaSpesaElement> listaSpesaElements = new ArrayList<>();


    public Ricetta(String titolo, String procedimento, DifficoltaRicetta difficoltaRicetta,
                   int tempoPreparazioneMinuti, int tempoCotturaMinuti,
                   CostoRicetta costoRicetta, Utente utente) {
        this.titolo = titolo;
        this.procedimento = procedimento;
        this.difficoltaRicetta = difficoltaRicetta;
        this.tempoPreparazioneMinuti = tempoPreparazioneMinuti;
        this.tempoCotturaMinuti = tempoCotturaMinuti;
        this.costoRicetta = costoRicetta;
        this.utente = utente;

    }

    @Override
    public String toString() {
        return "Ricetta{" +
                "titolo='" + titolo + '\'' +
                ", procedimento='" + procedimento + '\'' +
                ", difficoltaRicetta=" + difficoltaRicetta +
                ", tempoPreparazioneMinuti=" + tempoPreparazioneMinuti +
                ", tempoCotturaMinuti=" + tempoCotturaMinuti +
                ", costoRicetta=" + costoRicetta +
                '}';
    }
}
