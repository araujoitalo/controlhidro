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
@Table(name="PRECIPITACAO")
public class Precipitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPrecipitacao")
    private Integer idPrecipitacao;

    @Column(name = "IdParcela", nullable = false)
    private Integer idParcela;

    @Column(name = "DataPrecipitacao", nullable = false )
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPrecipitacao;

    @Column(name = "Chuva", nullable = false)
    private Float chuva;




}
