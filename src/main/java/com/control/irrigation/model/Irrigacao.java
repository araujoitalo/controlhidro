package com.control.irrigation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "IRRIGACAO")
public class Irrigacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Idrrigacao")
    private Integer idIrrigacao;

    @Column(name = "IdParcela", nullable = false)
    private Integer idParcela;

    @Column(name = "DataIrrigacao", nullable = false, updatable = false )
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataIrrigacao;

    @Column(name = "Pivo", nullable = false)
    private Float pivo;

    @Column(name = "Gotejo", nullable = false)
    private Float gotejo;

    @Column(name = "InicioIrrigacao", nullable = false)
    private LocalDateTime inicioIrrigacao;

    @Column(name = "FimIrrigacao")
    private LocalDateTime fimIrrigacao;

    @Column(name = "Quantidade", nullable = false)
    private Float quantidade;

    @Column(name = "VolumeConsumido")
    private Float volumeConsumido;

    @Column(name = "ValorEnergetico")
    private Float valorEnergetico;

    @Column(name = "KmConsumido")
    private Float kmConsumido;

    @Column(name = "Fertirrigacao")
    private Boolean fertirrigacao;

}
