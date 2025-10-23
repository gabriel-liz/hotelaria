package com.hotelaria.hotelaria.domain.repository;

import com.hotelaria.hotelaria.domain.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {
}
