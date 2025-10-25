package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.model.CheckIn;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;

@Service
public class CalculoHospedagemService {

    private static final BigDecimal VALOR_DIA_SEMANA = BigDecimal.valueOf(120);
    private static final BigDecimal VALOR_FIM_SEMANA = BigDecimal.valueOf(150);
    private static final BigDecimal GARAGEM_DIA_SEMANA = BigDecimal.valueOf(15);
    private static final BigDecimal GARAGEM_FIM_SEMANA = BigDecimal.valueOf(20);

    public BigDecimal calcularValor(CheckIn checkIn) {
        if (checkIn.getDataEntrada() == null || checkIn.getDataSaida() == null) {
            return BigDecimal.ZERO;
        }
        OffsetDateTime entrada = checkIn.getDataEntrada();
        OffsetDateTime saida = checkIn.getDataSaida();
        BigDecimal total = BigDecimal.ZERO;
        OffsetDateTime data = entrada;
        while (!data.toLocalDate().isAfter(saida.toLocalDate())) {
            boolean fimDeSemana = isFimDeSemana(data.getDayOfWeek());
            BigDecimal diaria = fimDeSemana ? VALOR_FIM_SEMANA : VALOR_DIA_SEMANA;
            BigDecimal valorGaragem = BigDecimal.ZERO;
            if (Boolean.TRUE.equals(checkIn.isAdicionalVeiculo())) {
                valorGaragem = fimDeSemana ? GARAGEM_FIM_SEMANA : GARAGEM_DIA_SEMANA;
            }
            total = total.add(diaria).add(valorGaragem);
            data = data.plusDays(1);
        }
        if (saida.toLocalTime().isAfter(java.time.LocalTime.of(16, 30))) {
            boolean fimDeSemanaSaida = isFimDeSemana(saida.getDayOfWeek());
            BigDecimal diariaExtra = fimDeSemanaSaida ? VALOR_FIM_SEMANA : VALOR_DIA_SEMANA;
            BigDecimal garagemExtra = BigDecimal.ZERO;

            if (Boolean.TRUE.equals(checkIn.isAdicionalVeiculo())) {
                garagemExtra = fimDeSemanaSaida ? GARAGEM_FIM_SEMANA : GARAGEM_DIA_SEMANA;
            }

            total = total.add(diariaExtra).add(garagemExtra);
        }
        return total;
    }

    private boolean isFimDeSemana(DayOfWeek dia) {
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
    }
}
