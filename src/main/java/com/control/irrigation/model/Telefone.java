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
@Table(name = "TELEFONE")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numero;

    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    @ManyToOne(optional = false)
    private Usuario usuario;

}
