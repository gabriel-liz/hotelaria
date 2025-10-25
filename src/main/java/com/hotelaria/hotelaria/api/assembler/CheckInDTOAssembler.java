package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.CheckInDTO;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckInDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CheckInDTO toModel(CheckIn checkIn) {
        return modelMapper.map(checkIn, CheckInDTO.class);
    }

    public List<CheckInDTO> toCollectionModel(List<CheckIn> checkIns) {
        return checkIns.stream()
                .map(checkIn -> toModel(checkIn))
                .collect(Collectors.toList());
    }
}
