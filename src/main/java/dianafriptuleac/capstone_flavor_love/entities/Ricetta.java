package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ricetta_categorie",
            joinColumns = @JoinColumn(name = "ricetta_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @ToString.Exclude
    @JsonIgnore
    private List<CategoriaRicetta> categorie = new ArrayList<>();

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImgRicetta> img = new ArrayList<>();

    public Ricetta(String titolo, String procedimento) {
        this.titolo = titolo;
        this.procedimento = procedimento;
    }

    @Override
    public String toString() {
        return "Ricetta{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", procedimento='" + procedimento + '\'' +
                '}';
    }
}
