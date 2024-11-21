package dianafriptuleac.capstone_flavor_love.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoriaRicetta {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descrizione;

    @ManyToMany(mappedBy = "categorie")
    @ToString.Exclude
    private List<Ricetta> ricette = new ArrayList<>();

    public CategoriaRicetta(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }
}
