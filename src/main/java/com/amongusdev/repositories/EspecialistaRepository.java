package com.amongusdev.repositories;

import com.amongusdev.models.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialistaRepository extends JpaRepository<Especialista, String> {
    @Modifying
    @Query(value = "SELECT area_id FROM especialista_has_area_especializacion WHERE area_id = ?1", nativeQuery = true)
    List<Integer> encontrarEspecialistasPorArea(int id);
}
