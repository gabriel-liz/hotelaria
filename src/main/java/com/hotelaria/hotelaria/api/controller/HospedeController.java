package com.hotelaria.hotelaria.api.controller;

import com.hotelaria.hotelaria.api.assembler.HospedeDTOAssembler;
import com.hotelaria.hotelaria.api.assembler.HospedeInputDTODisassembler;
import com.hotelaria.hotelaria.api.model.HospedeDTO;
import com.hotelaria.hotelaria.api.model.input.HospedeInputDTO;
import com.hotelaria.hotelaria.domain.exception.HospedeNaoEncontradoException;
import com.hotelaria.hotelaria.domain.exception.NegocioException;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import com.hotelaria.hotelaria.domain.service.HospedeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hospedes")

public class HospedeController {

    @Autowired
    private HospedeService hospedeService;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private HospedeDTOAssembler hospedeDTOAssembler;

    @Autowired
    private HospedeInputDTODisassembler hospedeInputDTODisassembler;

    @GetMapping
    public List<HospedeDTO> listar() {
        List<Hospede> todosHospedes = hospedeRepository.findAll();
        return hospedeDTOAssembler.toCollectionModel(todosHospedes);
    }

    @GetMapping("/fora-do-hotel")
    public List<HospedeDTO> listarHospedesForaDoHotel(){
        List<Hospede> hospedesForadoHotel = hospedeRepository.findHospedesAtualmenteForaDoHotel();
        return hospedeDTOAssembler.toCollectionModel(hospedesForadoHotel);
    }

    @GetMapping("/estao-no-hotel")
    public List<HospedeDTO> listarHospedesNoHotel(){
        List<Hospede> hospedesForadoHotel = hospedeRepository.findHospedesNoHotel();
        return hospedeDTOAssembler.toCollectionModel(hospedesForadoHotel);
    }


//    @GetMapping("/{hospedeId}")
//    public HospedeDTO buscar(@PathVariable Long hospedeId) {
//        Hospede hospede =hospedeService.buscarOuFalhar(hospedeId);
//        return hospedeDTOAssembler.toModel(hospede);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HospedeDTO adicionar(@RequestBody @Valid HospedeInputDTO hospedeInputDTO){
        try{
            Hospede hospede = hospedeInputDTODisassembler.toDomainObject(hospedeInputDTO);
            hospede = hospedeService.salvar(hospede);
            return hospedeDTOAssembler.toModel(hospede);
        } catch (HospedeNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{hospedeId}")
    public HospedeDTO atualizar(@PathVariable Long hospedeId, @RequestBody @Valid HospedeInputDTO hospedeInputDTO){
        try{
            Hospede hospedeAtual = hospedeService.buscarOuFalhar(hospedeId);

            hospedeInputDTODisassembler.copyToDomainObject(hospedeInputDTO, hospedeAtual);
            hospedeAtual = hospedeService.salvar(hospedeAtual);
            return hospedeDTOAssembler.toModel(hospedeAtual);
        }catch (HospedeNaoEncontradoException e){
            throw  new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{hospedeId}")
    public void remover(@PathVariable Long hospedeId){
        hospedeService.excluir(hospedeId);
    }

}
