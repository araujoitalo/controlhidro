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
@Table(name="OUTORGA_FASE")
public class OutorgaFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOutorgaFase")
    private Integer idOutorgaFase;

    @Column(name = "IdOutorga", nullable = false)
    private Integer idOutorga;

    @Column(name = "NomeFase", nullable = false, length = 30)
    private String nomeFase;

    @Column(name = "LimiteFase", nullable = false)
    private Double limiteFase;

    @Column(name = "InicioFase", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inicioFase;

    @Column(name = "FimFase", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fimFase;

}
