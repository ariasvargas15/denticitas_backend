package com.amongusdev.repositories;

import com.amongusdev.models.HojaVida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HojaVidaRepository extends JpaRepository<HojaVida, String> {
}
