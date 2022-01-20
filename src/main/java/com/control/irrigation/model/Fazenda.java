package com.control.irrigation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FAZENDA")
public class Fazenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFazenda")
    private Integer idFazenda;

    @Column(name = "IdUsuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "NomeFazenda", nullable = false, length = 150)
    private String nomeFazenda;

    @Column(name = "AreaFazenda", nullable = false)
    private Double area;

    @Column(name = "AltitudeFazenda")
    private Double altitude;

    @Column(name = "DataCadastro", updatable = false )
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    @OneToMany
    @JoinColumn(name = "idFazenda", foreignKey = @ForeignKey(name = "FK_CLIMA_FAZENDA"))
    private List<Clima> climas;

    @OneToMany
    @JoinColumn(name = "idFazenda", foreignKey = @ForeignKey(name = "FK_CULTURA_FAZENDA"))
    private List<Cultura> culturas;

    @OneToMany
    @JoinColumn(name = "idFazenda", foreignKey = @ForeignKey(name = "FK_OUTORGA_FAZENDA"))
    private List<Outorga> outorgas;

    @OneToMany
    @JoinColumn(name = "idFazenda", foreignKey = @ForeignKey(name = "FK_GOTEJADOR_FAZENDA"))
    private List<Gotejador> gotejadores;

    @OneToMany
    @JoinColumn(name = "idFazenda", foreignKey = @ForeignKey(name = "FK_PARCELA_FAZENDA"))
    private List<Parcela> parcelas;



}
