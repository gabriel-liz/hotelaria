package com.hotelaria.hotelaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelaria.hotelaria.api.assembler.HospedeDTOAssembler;
import com.hotelaria.hotelaria.api.assembler.HospedeDetalheValoresDTOAssembler;
import com.hotelaria.hotelaria.api.assembler.HospedeInputDTODisassembler;
import com.hotelaria.hotelaria.api.controller.HospedeController;
import com.hotelaria.hotelaria.api.model.HospedeDTO;
import com.hotelaria.hotelaria.api.model.HospedesValoresDTO;
import com.hotelaria.hotelaria.api.model.input.HospedeInputDTO;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import com.hotelaria.hotelaria.domain.service.HospedeService;
import com.hotelaria.hotelaria.domain.service.PagamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HospedeController.class)
class HospedeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HospedeService hospedeService;

    @MockitoBean
    private PagamentoService pagamentoService;

    @MockitoBean
    private HospedeRepository hospedeRepository;

    @MockitoBean
    private HospedeDTOAssembler hospedeDTOAssembler;

    @MockitoBean
    private HospedeDetalheValoresDTOAssembler hospedeDetalheValoresDTOAssembler;

    @MockitoBean
    private HospedeInputDTODisassembler hospedeInputDTODisassembler;

    @Test
    @DisplayName("Deve retornar a lista de hóspedes com sucesso")
    void deveListarHospedesComSucesso() throws Exception {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        hospede.setNome("João");
        hospede.setDocumento("123");
        hospede.setTelefone("999");

        HospedeDTO dto = new HospedeDTO();
        dto.setNome("João");
        dto.setDocumento("123");
        dto.setTelefone("999");

        when(hospedeService.buscarOuFalhar(any())).thenReturn(hospede);
        when(hospedeDTOAssembler.toCollectionModel(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/hospedes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    @DisplayName("Deve buscar hóspede com valores agregados")
    void deveBuscarHospedeComValores() throws Exception {
        Hospede hospede = new Hospede();
        hospede.setId(1L);
        hospede.setNome("João");

        PagamentoService.ValoresAgregadosDTO agregados = new PagamentoService.ValoresAgregadosDTO(
                BigDecimal.valueOf(500), BigDecimal.valueOf(200)
        );

        HospedesValoresDTO resposta = new HospedesValoresDTO();
        resposta.setNome("João");
        resposta.setValorTotalGasto(BigDecimal.valueOf(500));
        resposta.setValorUltimaHospedagem(BigDecimal.valueOf(200));

        when(hospedeService.buscarOuFalhar(1L)).thenReturn(hospede);
        when(pagamentoService.calcularValoresAgregados(1L)).thenReturn(agregados);
        when(hospedeDetalheValoresDTOAssembler.toModelComAgregados(hospede, agregados))
                .thenReturn(resposta);

        mockMvc.perform(get("/hospedes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorTotalGasto").value(500))
                .andExpect(jsonPath("$.valorUltimaHospedagem").value(200));
    }

    @Test
    @DisplayName("Deve adicionar um hóspede com sucesso")
    void deveAdicionarHospedeComSucesso() throws Exception {
        HospedeInputDTO input = new HospedeInputDTO();
        input.setNome("Maria");
        input.setDocumento("987");
        input.setTelefone("888");

        Hospede hospedeSalvo = new Hospede();
        hospedeSalvo.setId(10L);
        hospedeSalvo.setNome("Maria");
        hospedeSalvo.setDocumento("987");
        hospedeSalvo.setTelefone("888");

        HospedeDTO dto = new HospedeDTO();
        dto.setNome("Maria");
        dto.setDocumento("987");
        dto.setTelefone("888");

        when(hospedeInputDTODisassembler.toDomainObject(any())).thenReturn(hospedeSalvo);
        when(hospedeService.salvar(any())).thenReturn(hospedeSalvo);
        when(hospedeDTOAssembler.toModel(any())).thenReturn(dto);

        mockMvc.perform(post("/hospedes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.documento").value("987"));
    }
}
