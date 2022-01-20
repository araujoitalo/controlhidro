package com.control.irrigation.controller;

import com.control.irrigation.model.CulturaFase;
import com.control.irrigation.model.Parcela;
import com.control.irrigation.repository.CulturaFaseRepository;
import com.control.irrigation.repository.ParcelaRepository;
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
@RequestMapping("/parcelas")
public class ParcelaController {

    @Autowired
    private ParcelaRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheparcelas", allEntries = true)
    @CachePut("cacheparcelas")
    public ResponseEntity<List<Parcela>> obterTodos(){
        List<Parcela> list = (List<Parcela>) repository.findAll();
        return new ResponseEntity<List<Parcela>>(list, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Parcela salvar(@RequestBody @Valid Parcela parcela){

        return repository.save(parcela);
    }

    @GetMapping("{id}")
    public Parcela acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Parcela não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( parcela -> {
                    repository.delete(parcela);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não foi possível deletar a Parcela") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Parcela parcelaAtualizado ) {
        repository
                .findById(id)
                .map( parcela -> {
                    parcela.setNomeParcela((parcelaAtualizado.getNomeParcela()));


                    return repository.save(parcela);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Parcela não encontrada") );
    }


}

