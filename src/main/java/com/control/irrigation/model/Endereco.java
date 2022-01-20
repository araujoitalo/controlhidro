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
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150)
    private String cep;

    @Column(length = 150)
    private String logradouro;

    @Column(length = 150)
    private String numero;

    @Column(length = 150)
    private String complemento;

    @Column(length = 150)
    private String bairro;

    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval= true)
    @JoinColumn(name="cidade_id")
    private Cidade cidade;
}

