package com.amongusdev.repositories;

import com.amongusdev.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    @Modifying
    @Query(value = "SELECT area_id FROM servicio WHERE area_id = ?1", nativeQuery = true)
    List<Integer> encontrarServiciosPorArea(int id);
}
