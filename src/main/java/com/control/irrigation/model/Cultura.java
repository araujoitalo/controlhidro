package com.control.irrigation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="CULTURA")
public class Cultura {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "IdCultura")
    private Integer idCultura;

    @Column(name = "IdFazenda", nullable = false)
    private Integer idFazenda;

    @Column(name = "NomeCultura", nullable = false)
    private String nomeCultura;

    @OneToMany
    @JoinColumn(name = "idCultura", foreignKey = @ForeignKey(name = "FK_PARCELA_CULTURA"))
    private List<Parcela> parcelas;

    @OneToMany
    @JoinColumn(name = "idCultura", foreignKey = @ForeignKey(name = "FK_CULTURAFASE_CULTURA"))
    private List<CulturaFase> culturaFases;

}
