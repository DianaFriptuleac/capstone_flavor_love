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
@Table(name = "ricette_esterne")
@Getter
@Setter
@NoArgsConstructor
public class RicetteEsterne {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String title;

    @Column(length = 10000)
    private String instructions;

    private String image;

    @OneToMany(mappedBy = "ricetteEsterne", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IngredientiRicettaEsterna> ingredienti = new ArrayList<>();

    public RicetteEsterne(String title, String instructions, String image) {
        this.title = title;
        this.instructions = instructions;
        this.image = image;
    }
}
