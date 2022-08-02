package com.control.irrigation.controller;

import com.control.irrigation.model.Clima;
import com.control.irrigation.model.Fazenda;
import com.control.irrigation.model.Irrigacao;
import com.control.irrigation.repository.ClimaRepository;
import com.control.irrigation.repository.IrrigacaoRepository;
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
@RequestMapping("/irrigacoes")
@RequiredArgsConstructor
public class IrrigacaoController  {

    @Autowired
    private IrrigacaoRepository repository;

    @GetMapping(value = "/", produces = "application/json")
    @CacheEvict(value = "cacheirrigacao", allEntries = true)
    @CachePut("cacheirrigacao")
    public ResponseEntity<List<Irrigacao>> obterTodos(){
        List<Irrigacao> list = (List<Irrigacao>) repository.findAll();
        return new ResponseEntity<List<Irrigacao>>(list, HttpStatus.OK);

    }

    @GetMapping("/buscaIrrigacoes/{idParcela}")
    @CacheEvict(value = "cacheirrigacaoid", allEntries = true)
    @CachePut("cacheirrigacaoid")
    public ResponseEntity<List<Irrigacao>> obterFazendasPorIdParcela(
            @PathVariable(value = "idParcela") Integer idParcela){
        List<Irrigacao> list = (List<Irrigacao>) repository.buscaIrrigacaoPorIdParcela(idParcela);
        return new ResponseEntity<List<Irrigacao>>(list, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Irrigacao salvar(@RequestBody @Valid Irrigacao irrigacao){

        return repository.save(irrigacao);
    }

    @GetMapping("{id}")
    public Irrigacao acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Irrigacao não encontrado") );
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( irrigacao -> {
                    repository.delete(irrigacao);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não foi possível deletar o Irrigacao") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Irrigacao irrigacaoAtualizado ) {
        repository
                .findById(id)
                .map( irrigacao -> {
                    irrigacao.setQuantidade(irrigacaoAtualizado.getQuantidade());
                    return repository.save(irrigacao);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Irrigacao não encontrado") );
    }


}
