package com.control.irrigation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "IRRIGACAO")
public class Irrigacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdIrrigacao")
    private Integer idIrrigacao;

    @Column(name = "IdParcela", nullable = false)
    private Integer idParcela;

    @Column(name = "DataIrrigacao", nullable = false, updatable = false )
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataIrrigacao;

    @Column(name = "Gotejo")
    private Float gotejo;

    @Column(name = "Quantidade", nullable = false)
    private Float quantidade;

    @Column(name = "Fertirrigacao")
    private Boolean fertirrigacao;

}
