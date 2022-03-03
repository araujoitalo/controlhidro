package com.control.irrigation.controller;

import com.control.irrigation.model.CulturaFase;
import com.control.irrigation.model.Fazenda;
import com.control.irrigation.model.Outorga;
import com.control.irrigation.model.Parcela;
import com.control.irrigation.repository.CulturaFaseRepository;
import com.control.irrigation.repository.ParcelaRepository;
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
@RequestMapping("/parcelas")
@RequiredArgsConstructor
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

    @GetMapping("/buscaParcelas/{idFazenda}")
    @CacheEvict(value = "cacheparcelaid", allEntries = true)
    @CachePut("cacheparcelaid")
    public ResponseEntity<List<Parcela>> obterParcelasPorIdFazenda(
            @PathVariable(value = "idFazenda") Integer idFazenda){
        List<Parcela> list = (List<Parcela>) repository.buscaParcelaPorIdFazenda(idFazenda);
        return new ResponseEntity<List<Parcela>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Parcela> salvar(@RequestBody Parcela parcela){

        Parcela parcelaSalvo = repository.save(parcela);

        return new ResponseEntity<Parcela>(parcelaSalvo, HttpStatus.OK);
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

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Parcela> alterar(@RequestBody Parcela parcela){

        Parcela parcelaSalvo = repository.save(parcela);

        return new ResponseEntity<Parcela>(parcelaSalvo, HttpStatus.OK);
    }

}

