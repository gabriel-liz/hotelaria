package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.HospedesValoresDTO;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.service.PagamentoService.ValoresAgregadosDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HospedeDetalheValoresDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public HospedesValoresDTO toModel(Hospede hospede) {
        return modelMapper.map(hospede, HospedesValoresDTO.class);
    }

    public HospedesValoresDTO toModelComAgregados(Hospede hospede, ValoresAgregadosDTO agregados) {
        HospedesValoresDTO dto = modelMapper.map(hospede, HospedesValoresDTO.class);
        dto.setValorTotalGasto(agregados.valorTotalGasto);
        dto.setValorUltimaHospedagem(agregados.valorUltimaHospedagem);
        return dto;
    }

    public List<HospedesValoresDTO> toCollectionModel(List<Hospede> hospedes) {
        return hospedes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}