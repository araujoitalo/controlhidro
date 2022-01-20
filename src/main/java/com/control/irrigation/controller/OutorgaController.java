package com.control.irrigation.controller;

import com.control.irrigation.model.Fazenda;
import com.control.irrigation.model.Outorga;
import com.control.irrigation.repository.FazendaRepository;
import com.control.irrigation.repository.OutorgaRepository;
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
@RequestMapping("/outorgas")
public class OutorgaController {

    @Autowired
    private OutorgaRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheoutorga", allEntries = true)
    @CachePut("cacheoutorga")
    public ResponseEntity<List<Outorga>> obterTodos(){
        List<Outorga> list = (List<Outorga>) repository.findAll();
        return new ResponseEntity<List<Outorga>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Outorga salvar(@RequestBody @Valid Outorga outorga){
        return repository.save(outorga);
    }

    @GetMapping("{id}")
    public Outorga acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outorga não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( outorga -> {
                    repository.delete(outorga);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar a Outorga") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Outorga outorgaAtualizado ) {
        repository
                .findById(id)
                .map( outorga -> {
                    outorga.setNomeOutorga((outorgaAtualizado.getNomeOutorga()));


                    return repository.save(outorga);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outorga não encontrada") );
    }


}
