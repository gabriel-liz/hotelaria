package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.exception.EntidadeNaoEncontradaException;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    public Hospede salvar(Hospede hospede){
        return hospedeRepository.save(hospede);
    }

    public void excluir(Long hospedeId){

        try{
            hospedeRepository.deleteById(hospedeId);
        }catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de hospede com código %d", hospedeId));
        }
    }


}
