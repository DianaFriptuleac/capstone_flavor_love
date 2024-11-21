package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingredienti")
@Getter
@Setter
@NoArgsConstructor
public class Ingrediente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    private String dosaggio;
    // "Per la pasta", "Per il sugo"...
    private String sezione;

    @ManyToMany(mappedBy = "ingredienti")
    @ToString.Exclude
    @JsonIgnore
    private List<Ricetta> ricetta = new ArrayList<>();

    public Ingrediente(String nome, String dosaggio, String sezione) {
        this.nome = nome;
        this.dosaggio = dosaggio;
        this.sezione = sezione;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dosaggio='" + dosaggio + '\'' +
                ", sezione='" + sezione + '\'' +
                '}';
    }
}
