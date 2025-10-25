package com.hotelaria.hotelaria.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Hospede {


    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false, unique = true)
    private String telefone;

    @OneToMany(mappedBy = "hospede")
    private List<CheckIn> checkins = new ArrayList<>();

    @OneToMany(mappedBy = "hospede")
    private List<Pagamento> pagamentos = new ArrayList<>();

    private BigDecimal valorTotalGasto;

    private BigDecimal valorUltimaHospedagem;

    @Column(nullable = false)
    private OffsetDateTime dataCadastro = OffsetDateTime.now();
}
