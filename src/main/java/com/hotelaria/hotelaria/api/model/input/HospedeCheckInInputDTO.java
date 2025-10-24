package com.hotelaria.hotelaria.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospedeCheckInInputDTO {

    private String nome;
    private String documento;
    private String telefone;
}
