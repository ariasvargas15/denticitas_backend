package com.amongusdev.repositories;

import com.amongusdev.models.DiaAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaAgendaRepository extends JpaRepository<DiaAgenda, Integer> {
    @Query(value = "SELECT * FROM dia_agenda WHERE agenda_id = ?1 AND dia = ?2", nativeQuery = true)
    DiaAgenda verificarExistenciaDiaAgenda(int agenda_id, int dia);
}
