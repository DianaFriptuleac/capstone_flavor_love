package dianafriptuleac.capstone_flavor_love.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String descrizione;
    private String image;

    public RicetteEsterne(String title, String descrizione, String image) {
        this.title = title;
        this.descrizione = descrizione;
        this.image = image;
    }
}
