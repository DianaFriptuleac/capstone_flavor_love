package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ricettari")
@Getter
@Setter
@NoArgsConstructor
public class Ricettario {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    @JsonIgnore
    private Utente utente;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "ricettario_ricette",
            joinColumns = @JoinColumn(name = "ricettario_id"),
            inverseJoinColumns = @JoinColumn(name = "ricetta_id")
    )
    @JsonIgnore
    private List<Ricetta> ricette = new ArrayList<>();

    public Ricettario(String nome, Utente utente) {
        this.nome = nome;
        this.utente = utente;
    }

    public void addRicetta(Ricetta ricetta) {
        this.ricette.add(ricetta);
    }

    public void removeRicetta(Ricetta ricetta) {
        this.ricette.remove(ricetta);
    }
}
