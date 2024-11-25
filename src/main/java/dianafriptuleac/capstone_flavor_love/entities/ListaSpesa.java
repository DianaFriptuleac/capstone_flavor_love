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
@Table(name = "lista_spesa")
@Getter
@Setter
@NoArgsConstructor
public class ListaSpesa {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "listaSpesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaSpesaElement> elements = new ArrayList<>();

    public void addElement(ListaSpesaElement element) {
        elements.add(element);
        element.setListaSpesa(this);
    }

    public void removeElement(ListaSpesaElement element) {
        elements.remove(element);
        element.setListaSpesa(null);
    }

    @Override
    public String toString() {
        return "ListaSpesa{" +
                "id=" + id +
                '}';
    }
}
