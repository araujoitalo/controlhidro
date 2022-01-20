package com.control.irrigation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "GOTEJADOR")
public class Gotejador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdGotejador")
    private Integer idGotejador;

    @Column(name = "IdFazenda", nullable = false)
    private Integer idFazenda;

    @Column(name = "NomeGotejador", nullable = false, length = 120)
    private String nomeGotejador;

    @Column(name = "VazaoGotejador", nullable = false)
    private Float vazaoGotejador;

    @Column(name = "EspacamentoGotejador", nullable = false)
    private Float espacamentoGotejador;

    @Column(name = "EspacamentoLinha", nullable = false)
    private Float espacamentoLinha;

    @Column(name = "LarguraFaixaMolhada", nullable = false)
    private Float larguraFaixaMolhada;

    @Column(name = "Uniformidade", nullable = false)
    private Float uniformidade;

    @Column(name = "LaminaGotejador", nullable = false)
    private Float laminaGotejador;

    @OneToMany
    @JoinColumn(name = "idGotejador", foreignKey = @ForeignKey(name = "FK_PARCELA_GOTEJADOR"))
    private List<Parcela> parcelas;


}
