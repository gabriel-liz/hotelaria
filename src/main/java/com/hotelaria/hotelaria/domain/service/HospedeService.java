package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.exception.HospedeNaoEncontradoException;
import com.hotelaria.hotelaria.domain.exception.NegocioException;
import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private CalculoHospedagemService calculoHospedagemService;

    @Transactional
    public Hospede salvar(Hospede hospede) {

        Optional<Hospede> existentePorDocumento = hospedeRepository.findByDocumento(hospede.getDocumento());
        if (existentePorDocumento.isPresent() && !existentePorDocumento.get().getId().equals(hospede.getId())) {
            throw new NegocioException("Já existe um hóspede cadastrado com o mesmo documento.");
        }

        if (hospede.getTelefone() != null) {
            Optional<Hospede> existentePorTelefone = hospedeRepository.findByTelefone(hospede.getTelefone());
            if (existentePorTelefone.isPresent() && !existentePorTelefone.get().getId().equals(hospede.getId())) {
                throw new NegocioException("Já existe um hóspede cadastrado com o mesmo telefone.");
            }
        }

        try {
            return hospedeRepository.save(hospede);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException("Não foi possível salvar o hóspede. Documento ou telefone já cadastrado.");
        }
    }

    @Transactional
    public void excluir(Long hospedeId) {
        buscarOuFalhar(hospedeId);
        try {
            hospedeRepository.deleteById(hospedeId);
            hospedeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(String.format(
                    "Hóspede com código %d não pode ser removido, pois está em uso (associado a check-ins ou pagamentos).", hospedeId));
        }
    }

    public Hospede buscarOuFalhar(Long hospedeId) {
        return hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new HospedeNaoEncontradoException(hospedeId));
    }
}
