package dianafriptuleac.capstone_flavor_love.services;

import dianafriptuleac.capstone_flavor_love.entities.CategoriaRicetta;
import dianafriptuleac.capstone_flavor_love.exceptions.BadRequestException;
import dianafriptuleac.capstone_flavor_love.payloads.NewCategoriaRicettaDTO;
import dianafriptuleac.capstone_flavor_love.repositories.CategoriaRicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategorieRicettaService {

    @Autowired
    private CategoriaRicettaRepository categoriaRicettaRepository;


    public CategoriaRicetta creaCategoria(NewCategoriaRicettaDTO body) {
        if (categoriaRicettaRepository.existsByNome(body.nome())) {
            throw new BadRequestException("La categoria con il nome " + body.nome() + " esiste già!");
        }
        CategoriaRicetta categoriaRicetta = new CategoriaRicetta(body.nome(), body.descrizione());
        return categoriaRicettaRepository.save(categoriaRicetta);
    }


    public Page<CategoriaRicetta> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.categoriaRicettaRepository.findAll(pageable);
    }


    public CategoriaRicetta getCategoriaByNome(String nome) {
        return categoriaRicettaRepository.findByNome(nome).orElseThrow(() ->
                new BadRequestException("La categoria con il nome " + nome + " non è stata trovata!"));
    }


    public void deleteCategoria(String nome) {
        CategoriaRicetta categoriaRicetta = categoriaRicettaRepository.findByNome(nome)
                .orElseThrow(() ->
                        new BadRequestException("La categoria con il nome " + nome + " non è stata trovata!"));
        categoriaRicettaRepository.delete(categoriaRicetta);
    }
}
