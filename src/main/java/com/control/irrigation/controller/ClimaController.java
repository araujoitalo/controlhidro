package com.control.irrigation.controller;

import com.control.irrigation.model.Clima;
import com.control.irrigation.repository.ClimaRepository;
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
@RequestMapping("/climas")
public class ClimaController {

    @Autowired
    private ClimaRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheclima", allEntries = true)
    @CachePut("cacheclima")
    public ResponseEntity<List<Clima>> obterTodos(){
        List<Clima> list = (List<Clima>) repository.findAll();
        return new ResponseEntity<List<Clima>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Clima salvar(@RequestBody @Valid Clima clima){

        return repository.save(clima);
    }

    @GetMapping("{id}")
    public Clima acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clima não encontrado") );
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( clima -> {
                    repository.delete(clima);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar o Clima") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Clima climaAtualizado ) {
        repository
                .findById(id)
                .map( clima -> {
                    clima.setPrecipitacao(climaAtualizado.getPrecipitacao());
                    return repository.save(clima);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clima não encontrada") );
    }


}
