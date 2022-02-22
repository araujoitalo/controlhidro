package com.control.irrigation.repository;

import com.control.irrigation.model.Cultura;
import com.control.irrigation.model.Gotejador;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GotejadorRepository extends CrudRepository<Gotejador, Integer> {

    @Query("select u from Gotejador u where u.idFazenda = ?1")
    List<Gotejador> buscaGotejadorPorIdFazenda(Integer idFazenda);
}
