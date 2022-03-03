package com.control.irrigation.repository;

import com.control.irrigation.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Integer> {

    @Query("select u from Parcela u where u.idFazenda = ?1")
    List<Parcela> buscaParcelaPorIdFazenda(Integer idFazenda);
}
