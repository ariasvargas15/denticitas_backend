package com.amongusdev.repositories;

import com.amongusdev.models.AreaEspecializacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaEspecializacionRepository extends JpaRepository<AreaEspecializacion, Integer> {
    @Query(value = "SELECT especialista_cedula FROM especialista_has_area_especializacion WHERE area_id = ?1", nativeQuery = true)
    List<String> findEspecialistasByArea(int areaId);
}
