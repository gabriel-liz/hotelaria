package com.hotelaria.hotelaria.api.assembler;

import com.hotelaria.hotelaria.api.model.PagamentoDTO;
import com.hotelaria.hotelaria.domain.model.Pagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PagamentoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PagamentoDTO toModel(Pagamento pagamento) {
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public List<PagamentoDTO> toCollectionModel(List<Pagamento> pagamentos) {
        return pagamentos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}