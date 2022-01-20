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
@Table(name = "MANEJO")
public class Manejo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdManejo")
    private Integer idManejo;

    @Column(name = "IdParcela", nullable = false )
    private Integer idParcela;

    @Column(name = "DataManejo", nullable = false )
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataManejo;

    @Column(name = "IdadeParcela", nullable = false)
    private Integer idadeParcela;

    @Column(name = "IrrigacaoNecessaria")
    private Float irrigacaoNecessaria;

    @Column(name = "IrrigacaoRealizada")
    private Float irrigacaoRealizada;

    @Column(name = "IrrigacaoDesnecessaria")
    private Float irrigacaoDesnecessaria;

    @Column(name = "Percentimetro")
    private Float Percentimetro;

    @Column(name = "ConsumoDiario")
    private Float consumoDiario;

    @Column(name = "Tempo")
    private Float tempo;

    @Column(name = "Ks")
    private Float ks;

    @Column(name = "Balanco")
    private Float balanco;

    @Column(name = "Eto")
    private Float eto;

    @Column(name = "Kc")
    private Float kc;

    @Column(name = "Kl")
    private Float kl;

    @Column(name = "Precipitacao")
    private Float precipitacao;

    @Column(name = "TempoIrrigadoGotejo")
    private Float tempoIrrigadoGotejo;

    @Column(name = "TempoIrrigadoPivo")
    private Float tempoIrrigadoPivo;

    @Column(name = "QuantidadeIrrigada")
    private Float quantidadeIrrigada;

    @Column(name = "ExtresseUltrapassado")
    private Float extresseUltrapassado;



}
