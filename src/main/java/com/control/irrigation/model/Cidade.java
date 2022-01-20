package com.control.irrigation.model;

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
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(length = 150)
    private String nome;

    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval= true)
    @JoinColumn(name="estado_id")
    private Estado estado;



}
