package com.control.irrigation.controller;

import com.control.irrigation.model.Cultura;
import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.CulturaFaseRepository;
import com.control.irrigation.repository.CulturaRepository;
import com.control.irrigation.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/culturas")
public class CulturaController {

    @Autowired
    private CulturaRepository repository;

    @Autowired
    private CulturaFaseRepository culturaFaseRepository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cachecultura", allEntries = true)
    @CachePut("cachecultura")
    public ResponseEntity<List<Cultura>> obterTodos(){
        List<Cultura> list = (List<Cultura>) repository.findAll();
        return new ResponseEntity<List<Cultura>>(list, HttpStatus.OK);

    }
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Cultura> salvar( @RequestBody Cultura cultura ){

        Cultura culturaSalvo = repository.save(cultura);

        return new ResponseEntity<Cultura>(culturaSalvo, HttpStatus.OK);
    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Cultura> alterar( @RequestBody Cultura cultura ){

        Cultura culturaSalvo = repository.save(cultura);

        return new ResponseEntity<Cultura>(culturaSalvo, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public Cultura acharPorId( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultura não encontrada") );
    }

    @DeleteMapping("{id}")
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( cultura -> {
                    repository.delete(cultura);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar a Cultura") );

    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public String delete(@PathVariable(value = "id") Integer id){

        repository.deleteById(id);

        return "ok";
    }

    @DeleteMapping(value = "/removerCulturaFase/{id}", produces = "application/json" )
    public String deleteCulturaFase(@PathVariable(value = "id") Integer id){
        culturaFaseRepository.deleteById(id);
        return "ok";
    }



}
