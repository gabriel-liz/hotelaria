package com.hotelaria.hotelaria.api.model.input;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospedeInputDTO {

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento;

    private String telefone;
}
