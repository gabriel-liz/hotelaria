package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.HospedesDetalheValoresDTO;
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

    public HospedesDetalheValoresDTO toModel(Hospede hospede) {
        return modelMapper.map(hospede, HospedesDetalheValoresDTO.class);
    }

    public HospedesDetalheValoresDTO toModelComAgregados(Hospede hospede, ValoresAgregadosDTO agregados) {
        HospedesDetalheValoresDTO dto = modelMapper.map(hospede, HospedesDetalheValoresDTO.class);
        dto.setValorTotalGasto(agregados.valorTotalGasto);
        dto.setValorUltimaHospedagem(agregados.valorUltimaHospedagem);
        return dto;
    }

    public List<HospedesDetalheValoresDTO> toCollectionModel(List<Hospede> hospedes) {
        return hospedes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}