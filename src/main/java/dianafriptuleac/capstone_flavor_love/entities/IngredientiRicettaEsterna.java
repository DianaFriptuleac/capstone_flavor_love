package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ingredienti_ricetta_esterna")
@Getter
@Setter
@NoArgsConstructor
public class IngredientiRicettaEsterna {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;
    private double amount;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "ricette_esterne_id")
    @JsonIgnore
    private RicetteEsterne ricetteEsterne;

    public IngredientiRicettaEsterna(String name, double amount, String unit, RicetteEsterne ricetteEsterne) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.ricetteEsterne = ricetteEsterne;
    }
}
