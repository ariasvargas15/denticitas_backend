package com.amongusdev.repositories;

import com.amongusdev.models.HojaVida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HojaVidaRepository extends JpaRepository<HojaVida, String> {
    @Query(value = "SELECT * FROM hoja_vida WHERE especialista_cedula = ?1", nativeQuery = true)
    HojaVida findByEspecialista(String especialistaCedula);
}
