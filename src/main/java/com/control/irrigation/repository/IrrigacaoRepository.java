package com.control.irrigation.repository;

import com.control.irrigation.model.Irrigacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IrrigacaoRepository extends CrudRepository<Irrigacao, Integer> {

    @Query("select i from Irrigacao i where i.idParcela = ?1")
    List<Irrigacao> buscaIrrigacaoPorIdParcela(Integer idParcela);
}
