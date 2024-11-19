package dianafriptuleac.capstone_flavor_love.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "img_ricette")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImgRicetta {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ricetta_id", nullable = false)
    private Ricetta ricetta;

    private String url;

    public ImgRicetta(Ricetta ricetta, String url) {
        this.ricetta = ricetta;
        this.url = url;
    }
}
