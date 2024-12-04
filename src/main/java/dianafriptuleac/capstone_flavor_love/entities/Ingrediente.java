package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "ricetta_id", nullable = false)
    @JsonBackReference
    private Ricetta ricetta;

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
