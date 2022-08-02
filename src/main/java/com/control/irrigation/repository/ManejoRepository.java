package com.control.irrigation.repository;

import com.control.irrigation.model.Manejo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ManejoRepository extends CrudRepository<Manejo, Integer> {

    @Query("select u from Manejo u where u.idParcela = ?1")
    List<Manejo> buscaManejoPorParcela(Integer idParcela);

    @Query(value = "call sp_atualiza_manejo (:idParcela, :dataPlantio)", nativeQuery = true)
    Manejo atualizaManejo(Integer idParcela, String dataPlantio);
}
