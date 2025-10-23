package com.hotelaria.hotelaria.api.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospedeDTO {

    private Long id;

    private String nome;

    private String documento;

    private String telefone;
}
