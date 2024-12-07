package dianafriptuleac.capstone_flavor_love.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "liked")
@Getter
@Setter
@NoArgsConstructor
public class Liked {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToMany
    @JoinTable(
            name = "liked_ricette",
            joinColumns = @JoinColumn(name = "liked_id"),
            inverseJoinColumns = @JoinColumn(name = "ricetta_id")
    )
    private List<Ricetta> ricette = new ArrayList<>();

    public void addRicetta(Ricetta ricetta) {
        ricette.add(ricetta);
    }

    public void removeRicetta(Ricetta ricetta) {
        ricette.remove(ricetta);
    }
}
