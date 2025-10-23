package com.hotelaria.hotelaria.api.controller;

import com.hotelaria.hotelaria.domain.model.Hospede;
import com.hotelaria.hotelaria.domain.repository.HospedeRepository;
import com.hotelaria.hotelaria.domain.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hospedes")

public class HospedeController {

    @Autowired
    private HospedeService hospedeService;

    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping
    public List<Hospede> listar() {
        return hospedeRepository.findAll();
    }


    @GetMapping("/{hospedeId}")
    public ResponseEntity<Hospede> buscar(@PathVariable Long hospedeId){
        Optional<Hospede> hospede = hospedeRepository.findById(hospedeId);

        if(hospede.isPresent()){
            return ResponseEntity.ok(hospede.get());
        }
        return ResponseEntity.notFound().build();
    }
}
