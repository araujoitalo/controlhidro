package com.control.irrigation.repository;

import com.control.irrigation.model.Cultura;
import com.control.irrigation.model.Fazenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CulturaRepository extends CrudRepository<Cultura, Integer> {

    @Query("select u from Cultura u where u.idFazenda = ?1")
    List<Cultura> buscaCulturaPorIdFazenda(Integer idFazenda);
}
