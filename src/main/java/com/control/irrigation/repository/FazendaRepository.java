package com.control.irrigation.repository;

import com.control.irrigation.model.Fazenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FazendaRepository extends CrudRepository<Fazenda, Integer> {

    @Query("select u from Fazenda u where u.idUsuario = ?1")
    List<Fazenda> buscaFazendasPorIdUsuario(Integer idUsuario);

    @Query(value = "select * from FAZENDA f where f.idUsuario = :idUsuario LIMIT 1", nativeQuery = true)
    Fazenda buscaPrimeiraFazendaPorIdUsuario(Integer idUsuario);
}

