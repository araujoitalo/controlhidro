package com.control.irrigation.repository;

import com.control.irrigation.model.Irrigacao;
import org.springframework.data.repository.CrudRepository;

public interface IrrigacaoRepository extends CrudRepository<Irrigacao, Integer> {
}
