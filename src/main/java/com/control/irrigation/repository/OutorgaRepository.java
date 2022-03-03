package com.control.irrigation.repository;

import com.control.irrigation.model.Outorga;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OutorgaRepository extends CrudRepository<Outorga, Integer> {

    @Query("select o from Outorga o where o.idFazenda = ?1")
    List<Outorga> buscaOutorgaPorIdFazenda(Integer idFazenda);
}
