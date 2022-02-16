package com.control.irrigation.controller;

import com.control.irrigation.model.Manejo;
import com.control.irrigation.repository.ManejoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/manejos")
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
}
