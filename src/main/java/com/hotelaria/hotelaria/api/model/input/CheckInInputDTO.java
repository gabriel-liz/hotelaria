package com.hotelaria.hotelaria.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInInputDTO {

    @Valid
    @NotNull
    private HospedeIdInputDTO hospede;

    @NotNull
    private Boolean adicionalVeiculo;
}
