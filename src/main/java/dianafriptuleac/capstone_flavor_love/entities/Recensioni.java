package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recensioni")
@Getter
@Setter
@NoArgsConstructor
public class Recensioni {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private double stelle;

    private String commento;

    @Column(nullable = false)
    private LocalDateTime dataCreazione;
    // relazione utente
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    //relazione ricetta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ricetta_id", nullable = false)
    @JsonBackReference
    private Ricetta ricetta;

    public Recensioni(int stelle, String commento, LocalDateTime dataCreazione, Utente utente, Ricetta ricetta) {
        this.stelle = stelle;
        this.commento = commento;
        this.dataCreazione = dataCreazione;
        this.utente = utente;
        this.ricetta = ricetta;
    }
}
