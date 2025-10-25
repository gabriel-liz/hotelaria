package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.model.Pagamento;
import com.hotelaria.hotelaria.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public static class ValoresAgregadosDTO {
        public final BigDecimal valorTotalGasto;
        public final BigDecimal valorUltimaHospedagem;

        public ValoresAgregadosDTO(BigDecimal totalGasto, BigDecimal valorUltimaHospedagem) {
            this.valorTotalGasto = totalGasto;
            this.valorUltimaHospedagem = valorUltimaHospedagem;
        }
    }

    public ValoresAgregadosDTO calcularValoresAgregados(Long hospedeId) {

        BigDecimal totalGasto = Optional.ofNullable(pagamentoRepository.calcularValorTotalGasto(hospedeId))
                .orElse(BigDecimal.ZERO);
        BigDecimal valorUltima = pagamentoRepository.findUltimoPagamento(hospedeId)
                .map(Pagamento::getValorPago)
                .orElse(BigDecimal.ZERO);
        return new ValoresAgregadosDTO(totalGasto, valorUltima);
    }
}