package com.control.irrigation.controller;

import com.control.irrigation.model.CulturaFase;
import com.control.irrigation.repository.CulturaFaseRepository;
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
@RequestMapping("/culturafases")
public class CulturaFaseController {

    @Autowired
    private CulturaFaseRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheculturafases", allEntries = true)
    @CachePut("cacheculturafases")
    public ResponseEntity<List<CulturaFase>> obterTodos(){
        List<CulturaFase> list = (List<CulturaFase>) repository.findAll();
        return new ResponseEntity<List<CulturaFase>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CulturaFase salvar(@RequestBody @Valid CulturaFase culturaFase){
        return repository.save(culturaFase);
    }

    @GetMapping("{id}")
    public CulturaFase acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultura Fase não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( culturaFase -> {
                    repository.delete(culturaFase);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar a Cultura Fase") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid CulturaFase culturaFaseAtualizado ) {
        repository
                .findById(id)
                .map( culturaFase-> {
                    culturaFase.setInicioFase((culturaFaseAtualizado.getInicioFase()));


                    return repository.save(culturaFase);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultura Fase não encontrada") );
    }


}
