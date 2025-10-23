package com.hotelaria.hotelaria.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospedeIdInputDTO {

    @NotNull
    private Long id;
}
