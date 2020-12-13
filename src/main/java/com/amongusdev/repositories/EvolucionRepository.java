package com.amongusdev.repositories;

import com.amongusdev.models.Evolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvolucionRepository extends JpaRepository<Evolucion, Integer> {

    @Query(value = "SELECT * FROM evolucion WHERE id_historia = ?1", nativeQuery = true)
    List<Evolucion> findByHistoriaClinica(int idHistoria);

}
