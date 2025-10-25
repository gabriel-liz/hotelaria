package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.input.HospedeInputDTO;
import com.hotelaria.hotelaria.domain.model.Hospede;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospedeInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void configureModelMapper() {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true); // <- ESSA É A CHAVE
    }

    public Hospede toDomainObject(HospedeInputDTO cidadeInputDTO) {
        return modelMapper.map(cidadeInputDTO, Hospede.class);

    }

    public void copyToDomainObject(HospedeInputDTO hospedeInputDTO, Hospede hospede) {
        modelMapper.map(hospedeInputDTO, hospede);
    }

}
