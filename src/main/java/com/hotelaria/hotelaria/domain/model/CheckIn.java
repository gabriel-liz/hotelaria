package com.hotelaria.hotelaria.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode
@Entity
public class CheckIn {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataEntrada;

    private OffsetDateTime dataSaida;

    private boolean adicionalVeiculo;

    public void checkOut(){
        this.dataSaida = OffsetDateTime.now();
    }

}
