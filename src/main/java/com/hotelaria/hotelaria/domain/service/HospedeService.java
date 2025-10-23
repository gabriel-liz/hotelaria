package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.exception.EntidadeNaoEncontradaException;
import com.hotelaria.hotelaria.domain.exception.HospedeNaoEncontradoException;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    @Transactional
    public Hospede salvar(Hospede hospede){
        return hospedeRepository.save(hospede);
    }

    @Transactional
    public void excluir(Long hospedeId){

        try{
            hospedeRepository.deleteById(hospedeId);
        }catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de hospede com código %d", hospedeId));
        }
    }

    public Hospede buscarOuFalhar(Long hospedeId) {
        return hospedeRepository.findById(hospedeId).orElseThrow(() -> new HospedeNaoEncontradoException(hospedeId));
    }

}
