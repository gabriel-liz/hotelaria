package com.hotelaria.hotelaria.api.controller;

import com.hotelaria.hotelaria.api.assembler.CheckInDTOAssembler;
import com.hotelaria.hotelaria.api.assembler.CheckInInputDTODisassembler;
import com.hotelaria.hotelaria.api.model.CheckInDTO;
import com.hotelaria.hotelaria.api.model.input.CheckInInputDTO;
import com.hotelaria.hotelaria.domain.exception.CheckInNaoEncontradoException;
import com.hotelaria.hotelaria.domain.exception.NegocioException;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import com.hotelaria.hotelaria.domain.repository.CheckInRepository;
import com.hotelaria.hotelaria.domain.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/check-ins")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CheckInDTOAssembler checkInDTOAssembler;

    @Autowired
    private CheckInInputDTODisassembler checkInInputDTODisassembler;

    @GetMapping
    public List<CheckInDTO> listar() {
        List<CheckIn> todosCheckins = checkInRepository.findAll();
        return checkInDTOAssembler.toCollectionModel(todosCheckins);
    }

    @GetMapping("/{checkInId}")
    public CheckInDTO buscar(@PathVariable Long checkInId) {
        CheckIn checkIn = checkInService.buscarOuFalhar(checkInId);
        return checkInDTOAssembler.toModel(checkIn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckInDTO adicionar(@RequestBody @Valid CheckInInputDTO checkInInputDTO) {
        try {
            CheckIn checkIn = checkInInputDTODisassembler.toDomainObject(checkInInputDTO);
            checkIn = checkInService.salvar(checkIn);
            return checkInDTOAssembler.toModel(checkIn);
        } catch (CheckInNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{checkInId}/check-out")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkOut(@PathVariable Long checkInId) {
        checkInService.checkOut(checkInId);
    }

}
