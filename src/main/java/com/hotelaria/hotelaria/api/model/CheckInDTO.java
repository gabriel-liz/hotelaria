package com.hotelaria.hotelaria.api.model;

import com.hotelaria.hotelaria.api.model.input.HospedeCheckInInputDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CheckInDTO {

    private Long id;
    private HospedeCheckInInputDTO hospede;
    private OffsetDateTime dataEntrada;
    private OffsetDateTime dataSaida;
    private boolean adicionalVeiculo;

}
