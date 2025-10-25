package com.hotelaria.hotelaria.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PagamentoDTO {
    private Long id;
    private BigDecimal valorPago;
    private OffsetDateTime dataPagamento;
    private Long hospedeId;
}