package com.hotelaria.hotelaria.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class CheckIn {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    @Column(nullable = false)
    private OffsetDateTime dataEntrada;

    private OffsetDateTime dataSaida;

    private boolean adicionalVeiculo;

    public void checkOut() {
        this.dataSaida = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public void checkIn() {
        this.dataEntrada = OffsetDateTime.now(ZoneOffset.UTC);
    }

}
