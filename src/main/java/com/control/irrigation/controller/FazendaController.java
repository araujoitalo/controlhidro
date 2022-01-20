package com.control.irrigation.controller;

import com.control.irrigation.model.Fazenda;
import com.control.irrigation.repository.FazendaRepository;
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
@RequestMapping("/fazendas")
public class FazendaController {

    @Autowired
    private FazendaRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cachefazenda", allEntries = true)
    @CachePut("cachefazenda")
    public ResponseEntity<List<Fazenda>> obterTodos(){
        List<Fazenda> list = (List<Fazenda>) repository.findAll();
        return new ResponseEntity<List<Fazenda>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fazenda salvar(@RequestBody @Valid Fazenda fazenda){
        return repository.save(fazenda);
    }

    @GetMapping("{id}")
    public Fazenda acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fazenda não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( fazenda -> {
                    repository.delete(fazenda);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar a Fazenda") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Fazenda fazendaAtualizado ) {
        repository
                .findById(id)
                .map( fazenda -> {
                    fazenda.setNomeFazenda((fazendaAtualizado.getNomeFazenda()));
                    fazenda.setArea((fazendaAtualizado.getArea()));
                    fazenda.setAltitude((fazendaAtualizado.getAltitude()));

                    return repository.save(fazenda);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fazenda não encontrada") );
    }


}
