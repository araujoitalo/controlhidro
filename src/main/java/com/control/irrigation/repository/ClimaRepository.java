package com.control.irrigation.repository;

import com.control.irrigation.model.Clima;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClimaRepository extends CrudRepository<Clima, Integer> {

    @Query("select u from Clima u where u.idFazenda = ?1")
    List<Clima> buscaClimaPorIdFazenda(Integer idFazenda);
}
