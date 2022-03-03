package com.control.irrigation.repository;

import com.control.irrigation.model.Precipitacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrecipitacaoRepository extends CrudRepository<Precipitacao, Integer> {
    @Query("select p from Precipitacao p where p.idParcela = ?1")
    List<Precipitacao> buscaPrecipitacaoPorIdParcela(Integer idParcela);
}
