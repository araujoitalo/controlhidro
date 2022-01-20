package com.control.irrigation.repository;

import com.control.irrigation.model.Clima;
import org.springframework.data.repository.CrudRepository;

public interface ClimaRepository extends CrudRepository<Clima, Integer> {
}
