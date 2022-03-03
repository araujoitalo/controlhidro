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
@Table(name="OUTORGA")
public class Outorga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOutorga")
    private Integer idOutorga;

    @Column(name = "IdFazenda", nullable = false)
    private Integer idFazenda;

    @Column(name = "NomeOutorga", nullable = false, length = 50)
    private String nomeOutorga;

    @OneToMany
    @JoinColumn(name = "idOutorga", foreignKey = @ForeignKey(name = "FK_PARCELA_OUTORGA"))
    private List<Parcela> parcelas;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idOutorga", foreignKey = @ForeignKey(name = "FK_OUTORGAFASE_OUTORGA"))
    private List<OutorgaFase> outorgaFases;

}
