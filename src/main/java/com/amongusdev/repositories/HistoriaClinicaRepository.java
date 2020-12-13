package com.amongusdev.repositories;

import com.amongusdev.models.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Integer> {

    @Query(value = "SELECT * FROM historia_clinica WHERE cliente_cedula = ?1", nativeQuery = true)
    HistoriaClinica findByCliente(String clienteCedula);

}
