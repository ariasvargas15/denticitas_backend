package com.amongusdev.repositories;

import com.amongusdev.models.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    @Query(value = "SELECT * FROM turno WHERE hora_inicio = ?1 AND dia_agenda_id = ?2", nativeQuery = true)
    Turno verificarExistenciaTurno(String hora, int diaAgenda);

    @Query(value = "SELECT * FROM turno WHERE dia_agenda_id = ?1", nativeQuery = true)
    List<Turno> findByDiaAgenda(int diaAgenda);
}
