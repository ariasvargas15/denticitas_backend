package com.amongusdev.repositories;

import com.amongusdev.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    //Cristian
    @Modifying
    @Query(value = "SELECT cliente_cedula FROM cita WHERE cliente_cedula = ?1", nativeQuery = true)
    public List<Cita> findByCedula(String cedula);

}
