package com.hotelaria.hotelaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelaria.hotelaria.api.assembler.CheckInDTOAssembler;
import com.hotelaria.hotelaria.api.assembler.CheckInInputDTODisassembler;
import com.hotelaria.hotelaria.api.controller.CheckInController;
import com.hotelaria.hotelaria.api.model.CheckInDTO;
import com.hotelaria.hotelaria.api.model.input.CheckInInputDTO;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import com.hotelaria.hotelaria.domain.repository.CheckInRepository;
import com.hotelaria.hotelaria.domain.service.CheckInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckInController.class)
class CheckInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CheckInService checkInService;

    @MockitoBean
    private CheckInRepository checkInRepository;

    @MockitoBean
    private CheckInDTOAssembler checkInDTOAssembler;

    @MockitoBean
    private CheckInInputDTODisassembler checkInInputDTODisassembler;

    @Test
    @DisplayName("Deve listar todos os check-ins")
    void deveListarTodosOsCheckins() throws Exception {
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setId(1L);

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setId(2L);

        List<CheckIn> checkIns = Arrays.asList(checkIn1, checkIn2);

        CheckInDTO dto1 = new CheckInDTO();
        dto1.setId(1L);
        CheckInDTO dto2 = new CheckInDTO();
        dto2.setId(2L);

        given(checkInRepository.findAll()).willReturn(checkIns);
        given(checkInDTOAssembler.toCollectionModel(checkIns)).willReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/check-ins")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("Deve buscar um check-in por ID")
    void deveBuscarCheckinPorId() throws Exception {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(1L);

        CheckInDTO dto = new CheckInDTO();
        dto.setId(1L);

        given(checkInService.buscarOuFalhar(1L)).willReturn(checkIn);
        given(checkInDTOAssembler.toModel(checkIn)).willReturn(dto);

        mockMvc.perform(get("/check-ins/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve adicionar um novo check-in")
    void deveAdicionarCheckin() throws Exception {
        CheckInInputDTO inputDTO = new CheckInInputDTO();
        inputDTO.setAdicionalVeiculo(false);

        var hospedeInput = new com.hotelaria.hotelaria.api.model.input.HospedeCheckInInputDTO();
        hospedeInput.setNome("João da Silva");
        hospedeInput.setDocumento("12345678900");
        hospedeInput.setTelefone("11999999999");
        inputDTO.setHospede(hospedeInput);


        CheckIn checkIn = new CheckIn();
        checkIn.setId(10L);

        CheckInDTO dto = new CheckInDTO();
        dto.setId(10L);

        given(checkInInputDTODisassembler.toDomainObject(any(CheckInInputDTO.class))).willReturn(checkIn);
        given(checkInService.salvar(any(CheckIn.class))).willReturn(checkIn);
        given(checkInDTOAssembler.toModel(checkIn)).willReturn(dto);


        mockMvc.perform(post("/check-ins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    @DisplayName("Deve realizar check-out com sucesso")
    void deveRealizarCheckOut() throws Exception {
        doNothing().when(checkInService).checkOut(eq(1L));

        mockMvc.perform(put("/check-ins/1/check-out"))
                .andExpect(status().isNoContent());
    }
}
