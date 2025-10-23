package com.hotelaria.hotelaria.domain.repository;

import com.hotelaria.hotelaria.domain.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {
}
