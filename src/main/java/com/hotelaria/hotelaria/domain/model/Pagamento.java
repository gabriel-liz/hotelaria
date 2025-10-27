package com.hotelaria.hotelaria.domain.model;

import com.hotelaria.hotelaria.domain.exception.NegocioException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pagamento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorPago;

    @Column(nullable = false)
    private OffsetDateTime dataPagamento = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    @OneToOne
    @JoinColumn(name = "checkin_id", unique = true)
    private CheckIn checkIn;

    public void pagar(BigDecimal valor) {
        if (this.valorPago != null) {
            throw new NegocioException("O pagamento já foi realizado.");
        }

        this.valorPago = valor;
        this.dataPagamento = OffsetDateTime.now();
    }
}
