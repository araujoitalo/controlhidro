package com.control.irrigation.controller;

import com.control.irrigation.model.Irrigacao;
import com.control.irrigation.model.Precipitacao;
import com.control.irrigation.repository.PrecipitacaoRepository;
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
@RequestMapping("/precipitacoes")
@RequiredArgsConstructor
public class PrecipitacaoController{

    @Autowired
    private PrecipitacaoRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheprecipitacao", allEntries = true)
    @CachePut("cacheprecipitacao")
    public ResponseEntity<List<Precipitacao>> obterTodos(){
        List<Precipitacao> list = (List<Precipitacao>) repository.findAll();
        return new ResponseEntity<List<Precipitacao>>(list, HttpStatus.OK);

    }
    @GetMapping("/buscaPrecipitacoes/{idParcela}")
    @CacheEvict(value = "cacheprecipitacaoid", allEntries = true)
    @CachePut("cacheprecipitacaoid")
    public ResponseEntity<List<Precipitacao>> obterPrecipitacaoPorIdParcela(
            @PathVariable(value = "idParcela") Integer idParcela){
        List<Precipitacao> list = (List<Precipitacao>) repository.buscaPrecipitacaoPorIdParcela(idParcela);
        return new ResponseEntity<List<Precipitacao>>(list, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Precipitacao salvar(@RequestBody @Valid Precipitacao precipitacao){

        return repository.save(precipitacao);
    }

    @GetMapping("{id}")
    public Precipitacao acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Precipitacao não encontrado") );
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( precipitacao -> {
                    repository.delete(precipitacao);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não foi possível deletar a Precipitacao") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Precipitacao precipitacaoAtualizado ) {
        repository
                .findById(id)
                .map( precipitacao -> {
                    precipitacao.setChuva(precipitacaoAtualizado.getChuva());
                    return repository.save(precipitacao);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Precipitacao não encontrada") );
    }


}
