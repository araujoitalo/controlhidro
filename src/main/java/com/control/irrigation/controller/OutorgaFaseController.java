package com.control.irrigation.controller;

import com.control.irrigation.model.OutorgaFase;
import com.control.irrigation.repository.OutorgaFaseRepository;
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
@RequestMapping("/outorgafases")
public class OutorgaFaseController {

    @Autowired
    private OutorgaFaseRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheoutorgafases", allEntries = true)
    @CachePut("cacheoutorgafases")
    public ResponseEntity<List<OutorgaFase>> obterTodos(){
        List<OutorgaFase> list = (List<OutorgaFase>) repository.findAll();
        return new ResponseEntity<List<OutorgaFase>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutorgaFase salvar(@RequestBody @Valid OutorgaFase outorgaFase){
        return repository.save(outorgaFase);
    }

    @GetMapping("{id}")
    public OutorgaFase acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outorga Fase não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( outorgaFase -> {
                    repository.delete(outorgaFase);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar a Outorga Fase") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid OutorgaFase outorgaFaseAtualizado ) {
        repository
                .findById(id)
                .map( outorgaFase -> {
                    outorgaFase.setNomeFase((outorgaFase.getNomeFase()));


                    return repository.save(outorgaFase);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Outorga Fasenão encontrada") );
    }


}
