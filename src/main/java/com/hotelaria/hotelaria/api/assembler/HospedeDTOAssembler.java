package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.HospedeDTO;
import com.hotelaria.hotelaria.domain.model.Hospede;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HospedeDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public HospedeDTO toModel(Hospede hospede) {
        return modelMapper.map(hospede, HospedeDTO.class);
    }

    public List<HospedeDTO> toCollectionModel(List<Hospede> hospedes) {
        return hospedes.stream()
                .map(hospede -> toModel(hospede))
                .collect(Collectors.toList());
    }
}
