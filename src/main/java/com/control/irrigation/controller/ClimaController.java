package com.control.irrigation.controller;

import com.control.irrigation.model.Clima;
import com.control.irrigation.model.Cultura;
import com.control.irrigation.model.Gotejador;
import com.control.irrigation.repository.ClimaRepository;
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
@RequestMapping("/climas")
@RequiredArgsConstructor
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

    @GetMapping("/buscaClimas/{idFazenda}")
    @CacheEvict(value = "cacheclimaid", allEntries = true)
    @CachePut("cacheclimaid")
    public ResponseEntity<List<Clima>> obterClimasPorIdFazenda(@PathVariable(value = "idFazenda") Integer idFazenda){
        List<Clima> list = (List<Clima>) repository.buscaClimaPorIdFazenda(idFazenda);
        return new ResponseEntity<List<Clima>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Clima> salvar( @RequestBody Clima clima ){

        Clima climaSalvo = repository.save(clima);

        return new ResponseEntity<Clima>(climaSalvo, HttpStatus.OK);
    }
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Clima> alterar(@RequestBody Clima clima ){

        Clima climaSalvo = repository.save(clima);

        return new ResponseEntity<Clima>(climaSalvo, HttpStatus.OK);
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


}
