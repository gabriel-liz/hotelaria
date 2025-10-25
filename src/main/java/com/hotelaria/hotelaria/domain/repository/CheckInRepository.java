package com.hotelaria.hotelaria.domain.repository;

import com.hotelaria.hotelaria.domain.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
}
