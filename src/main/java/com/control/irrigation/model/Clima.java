package com.control.irrigation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CLIMA")
public class Clima{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdClima")
    private Integer idClima;

    @Column(name = "IdFazenda", nullable = false)
    private Integer idFazenda;

    @Column(name = "DataClima", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataClima;

    @Column(name = "TemperaturaMaxima", nullable = false)
    private Float temperaturaMaxima;

    @Column(name = "TemperaturaMinima", nullable = false)
    private Float temperaturaMinima;

    @Column(name = "TemperaturaMedia", nullable = false)
    private Float temperaturaMedia;

    @Column(name = "UmidadeRelativa", nullable = false)
    private Float umidadeRelativa;

    @Column(name = "VelocidadeVento", nullable = false)
    private Float velocidadeVento;

    @Column(name = "Precipitacao", nullable = false)
    private Float precipitacao;

    @Column(name = "RadiacaoMedia", nullable = false)
    private Float radiacaoMedia;

}
