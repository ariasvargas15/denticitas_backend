package com.amongusdev.repositories;

import com.amongusdev.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    @Query(value = "SELECT * FROM cita WHERE cliente_cedula = ?1 AND turno_id = ?2 AND servicio_id = ?3", nativeQuery = true)
    Cita verificarExistenciaCita(String clienteCedula, int turnoId, int servicioID);

    @Query(value = "SELECT * FROM cita WHERE turno_id = ?1", nativeQuery = true)
    List<Cita> findByTurno(int turnoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cita WHERE turno_id = ?1", nativeQuery = true)
    void deleteByTurno(int turnoId);

    @Query(value = "SELECT * FROM cita WHERE cliente_cedula = ?1", nativeQuery = true)
    List<Cita> findByCliente(String clienteCedula);
}
