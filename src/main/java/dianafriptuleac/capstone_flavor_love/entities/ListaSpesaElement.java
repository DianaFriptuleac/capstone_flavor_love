package dianafriptuleac.capstone_flavor_love.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "lista_spesa_element")
@Getter
@Setter
@NoArgsConstructor
public class ListaSpesaElement {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String dosaggio;
    private String sezione;
    private int quantita;

    @ManyToOne
    @JoinColumn(name = "lista_spesa_id", nullable = false)
    @JsonIgnore
    private ListaSpesa listaSpesa;

    @ManyToOne
    @JoinColumn(name = "ricetta_id", nullable = false)
    @JsonIgnore
    private Ricetta ricetta;

    public ListaSpesaElement(String nome, String dosaggio, String sezione, int quantita, Ricetta ricetta) {
        this.nome = nome;
        this.dosaggio = dosaggio;
        this.sezione = sezione;
        this.quantita = quantita;
        this.ricetta = ricetta;

    }

    @Override
    public String toString() {
        return "ListaSpesaElement{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dosaggio='" + dosaggio + '\'' +
                ", sezione='" + sezione + '\'' +
                ", quantita=" + quantita +
                '}';
    }
}
