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
@Table(name = "PARCELA")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdParcela")
    private Integer idParcela;

    @Column(name = "IdFazenda", nullable = false)
    private Integer idFazenda;

    @Column(name = "IdCultura", nullable = false)
    private Integer idCultura;

    @Column(name = "IdGotejador")
    private Integer idGotejador;

    @Column(name = "IdPivo")
    private Integer idPivo;

    @Column(name = "IdOutorga")
    private Integer idOutorga;

    @Column(name = "NomeParcela", nullable = false, length = 100)
    private String nomeParcela;

    @Column(name = "DataPlantio", nullable = false, updatable = false )
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPlantio;

    @Column(name = "AreaParcela", nullable = false)
    private Float areaParcela;

    @Column(name = "UmidadeSolo", nullable = false)
    private Float umidadeSolo;

    @Column(name = "ExtresseExcedido", nullable = false)
    private Float extresseExcedido;

    @Column(name ="Colonia", length = 10)
    private String colonia;

    @OneToMany
    @JoinColumn(name = "idParcela", foreignKey = @ForeignKey(name = "FK_IRRIGACAO_PARCELA"))
    private List<Irrigacao> irrigacoes;

    @OneToMany
    @JoinColumn(name = "idParcela", foreignKey = @ForeignKey(name = "FK_MANEJO_PARCELA"))
    private List<Manejo> manejos;

    @OneToMany
    @JoinColumn(name = "idParcela", foreignKey = @ForeignKey(name = "FK_PRECEPITACAO_PARCELA"))
    private List<Precipitacao> precipitacaos;

}
