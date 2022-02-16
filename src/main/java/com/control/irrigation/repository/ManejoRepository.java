package com.control.irrigation.repository;

import com.control.irrigation.model.Manejo;
import com.control.irrigation.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManejoRepository extends CrudRepository<Manejo, Integer> {

    @Query("select u from Manejo u where u.idParcela = ?1")
    List<Manejo> buscaManejoPorParcela(Integer idParcela);
}
