package com.hotelaria.hotelaria.domain.repository;

import com.hotelaria.hotelaria.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT SUM(p.valorPago) FROM Pagamento p WHERE p.hospede.id = :hospedeId")
    BigDecimal calcularValorTotalGasto(Long hospedeId);

    @Query("SELECT p FROM Pagamento p WHERE p.hospede.id = :hospedeId ORDER BY p.dataPagamento DESC LIMIT 1")
    Optional<Pagamento> findUltimoPagamento(Long hospedeId);
}
