package com.hotelaria.hotelaria.domain.service;

import com.hotelaria.hotelaria.domain.exception.CheckInNaoEncontradoException;
import com.hotelaria.hotelaria.domain.model.CheckIn;
import com.hotelaria.hotelaria.domain.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Transactional
    public CheckIn salvar(CheckIn checkIn){
        return checkInRepository.save(checkIn);
    }

    public CheckIn buscarOuFalhar(Long checkInId){
        return checkInRepository.findById(checkInId).orElseThrow(() -> new CheckInNaoEncontradoException(checkInId));
    }

    @Transactional
    public void checkOut(Long checkInId) {
        CheckIn checkIn = buscarOuFalhar(checkInId);
        checkIn.checkOut();
    }
}
