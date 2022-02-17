package com.control.irrigation.repository;

import com.control.irrigation.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ParcelaRepository extends JpaRepository<Parcela, Integer> {
}
