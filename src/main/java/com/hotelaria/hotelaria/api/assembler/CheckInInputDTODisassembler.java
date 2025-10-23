package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.input.CheckInInputDTO;
import com.hotelaria.hotelaria.api.model.input.HospedeInputDTO;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import com.hotelaria.hotelaria.domain.model.Hospede;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckInInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public CheckIn toDomainObject(CheckInInputDTO checkInInputDTO) {
        return modelMapper.map(checkInInputDTO, CheckIn.class);

    }

    public void copyToDomainObject(CheckInInputDTO checkInInputDTO, CheckIn checkIn) {
        modelMapper.map(checkInInputDTO, checkIn);
    }

}
