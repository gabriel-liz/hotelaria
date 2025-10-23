package com.hotelaria.hotelaria.api.model;

import com.hotelaria.hotelaria.api.model.input.HospedeIdInputDTO;
import com.hotelaria.hotelaria.domain.model.Hospede;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CheckInDTO {

    private Long id;
    private HospedeIdInputDTO hospede;
    private OffsetDateTime dataEntrada;
    private OffsetDateTime dataSaida;
    private boolean adicionalVeiculo;

}
