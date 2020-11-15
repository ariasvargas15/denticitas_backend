package com.amongusdev.repositories;

import com.amongusdev.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    @Query(value = "SELECT * FROM cita WHERE cliente_cedula = ?1 AND turno_id = ?2", nativeQuery = true)
    Cita verificarExistenciaCita(String clienteCedula, int turnoId);

    @Query(value = "SELECT * FROM cita WHERE turno_id = ?1", nativeQuery = true)
    Cita buscarCitaPorTurno(int turnoId);

    @Query(value = "SELECT * FROM cita WHERE cliente_cedula = ?1", nativeQuery = true)
    List<Cita> findByCliente(String clienteCedula);
}
