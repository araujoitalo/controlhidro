package com.control.irrigation.controller;

import com.control.irrigation.model.Manejo;
import com.control.irrigation.model.Usuario;
import com.control.irrigation.repository.ManejoRepository;
import com.sun.org.apache.regexp.internal.RE;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/manejos")
@RequiredArgsConstructor
public class ManejoController {

    @Autowired
    private ManejoRepository repository;


    /*Serviço RESTful*/
    @GetMapping(value = "/{id}", produces = "application/json")
    @CacheEvict(value = "cachemanejo", allEntries = true)
    @CachePut("cachemanejo")
    public ResponseEntity<List<Manejo>> buscaManejoPorFazenda(@PathVariable(value = "id") Integer id){

        List<Manejo> list = (List<Manejo>) repository.buscaManejoPorParcela(id);
        return new ResponseEntity<List<Manejo>>(list, HttpStatus.OK);
    }


    @GetMapping(value = "/atualizaManejo/{idParcela}/{dataPlantio}", produces = "application/json")
    public ResponseEntity<Manejo> atualizaManejo(
            @PathVariable(value = "idParcela") Integer id,
            @PathVariable(value = "dataPlantio") String data) {

        Manejo manejos = repository.atualizaManejo(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(manejos);
    }
}
