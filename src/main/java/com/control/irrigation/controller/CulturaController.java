package com.control.irrigation.controller;

import com.control.irrigation.model.Cultura;
import com.control.irrigation.repository.CulturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/culturas")
public class CulturaController {

    @Autowired
    private CulturaRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cachecultura", allEntries = true)
    @CachePut("cachecultura")
    public ResponseEntity<List<Cultura>> obterTodos(){
        List<Cultura> list = (List<Cultura>) repository.findAll();
        return new ResponseEntity<List<Cultura>>(list, HttpStatus.OK);

    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cultura salvar( @RequestBody @Valid Cultura cultura ){

        return repository.save(cultura);
    }

    @GetMapping("{id}")
    public Cultura acharPorId( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultura não encontrada") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( cultura -> {
                    repository.delete(cultura);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar a Cultura") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Cultura culturaAtualizado ) {
        repository
                .findById(id)
                .map( cultura -> {
                    cultura.setNomeCultura(culturaAtualizado.getNomeCultura());
                    return repository.save(cultura);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultura não encontrada") );
    }


}
