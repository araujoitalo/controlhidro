package com.control.irrigation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="CULTURA_FASE")
public class CulturaFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCulturaFase")
    private Integer idCulturaFase;

    @Column(name = "IdCultura", nullable = false)
    private Integer idCultura;

    @Column(name = "NomeFase", nullable = false, length = 50)
    private String nomeFase;

    @Column(name = "InicioFase", nullable = false)
    private Integer inicioFase;

    @Column(name = "FimFase", nullable = false)
    private Integer fimFase;

    @Column(name = "Kc", nullable = false)
    private Float kc;

    @Column(name = "ProfundidadeRaiz", nullable = false)
    private Float profundidadeRaiz;

    @Column(name = "PontoMurcha", nullable = false)
    private Float pontoMurcha;

    @Column(name = "CapacidadeCampo", nullable = false)
    private Float capacidadeCampo;

    @Column(name = "AreaSombreada", nullable = false)
    private Float areaSombreada;

    @Column(name = "Limite", nullable = false)
    private Float limite;

    @Column(name = "DensidadeAparente", nullable = false)
    private Float densidadeAparente;

}
