package com.amongusdev.repositories;

import com.amongusdev.models.DiaAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaAgendaRepository extends JpaRepository<DiaAgenda, Integer> {
}
