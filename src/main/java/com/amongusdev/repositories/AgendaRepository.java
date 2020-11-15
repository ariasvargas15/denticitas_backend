package com.amongusdev.repositories;

import com.amongusdev.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    @Query(value = "SELECT * FROM agenda WHERE mes = ?1 AND anio = ?2 AND especialista_cedula = ?3", nativeQuery = true)
    Agenda verificarExistenciaAgenda(int mes, int anio, String cedula);

    @Query(value = "SELECT * FROM agenda WHERE especialista_cedula = ?1", nativeQuery = true)
    List<Agenda> findByEspecialista(String cedula);
}
