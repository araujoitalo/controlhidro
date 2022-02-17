package com.control.irrigation.controller;

import com.control.irrigation.model.Cultura;
import com.control.irrigation.model.Fazenda;
import com.control.irrigation.model.Outorga;
import com.control.irrigation.repository.FazendaRepository;
import com.control.irrigation.repository.OutorgaRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Outorga> salvar(@RequestBody Outorga outorga){

        Outorga outorgaSalvo = repository.save(outorga);

        return new ResponseEntity<Outorga>(outorgaSalvo, HttpStatus.OK);
    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Outorga> alterar(@RequestBody Outorga outorga ){

        Outorga outorgaSalvo = repository.save(outorga);

        return new ResponseEntity<Outorga>(outorgaSalvo, HttpStatus.OK);
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

}
