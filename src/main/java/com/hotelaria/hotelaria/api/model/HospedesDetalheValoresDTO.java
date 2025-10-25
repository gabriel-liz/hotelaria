package com.hotelaria.hotelaria.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HospedesDetalheValoresDTO {
    private String nome;
    private BigDecimal valorTotalGasto;
    private BigDecimal valorUltimaHospedagem;
}
