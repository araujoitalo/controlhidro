package com.control.irrigation.controller;

import com.control.irrigation.model.Gotejador;
import com.control.irrigation.repository.GotejadorRepository;
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
@RequestMapping("/gotejadores")
public class GotejadorController {

    @Autowired
    private GotejadorRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cachegotejador", allEntries = true)
    @CachePut("cachegotejador")
    public ResponseEntity<List<Gotejador>> obterTodos(){
        List<Gotejador> list = (List<Gotejador>) repository.findAll();
        return new ResponseEntity<List<Gotejador>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Gotejador salvar(@RequestBody @Valid Gotejador gotejador){

        return repository.save(gotejador);
    }

    @GetMapping("{id}")
    public Gotejador acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gotejador não encontrado") );
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( gotejador -> {
                    repository.delete(gotejador);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar o Gotejador") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Gotejador gotejadorAtualizado ) {
        repository
                .findById(id)
                .map( gotejador -> {

                    return repository.save(gotejador);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gotejador não encontrada") );
    }


}
