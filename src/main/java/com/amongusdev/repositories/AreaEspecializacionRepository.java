package com.amongusdev.repositories;

import com.amongusdev.models.AreaEspecializacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AreaEspecializacionRepository extends JpaRepository<AreaEspecializacion, Integer> {
    @Query(value = "SELECT especialista_cedula FROM especialista_has_area_especializacion WHERE area_id = ?1", nativeQuery = true)
    List<String> findEspecialistasByArea(int areaId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO especialista_has_area_especializacion (area_id, especialista_cedula) VALUES (?1, ?2)", nativeQuery = true)
    void insertarAreaEspecialista(int areaId, String cedulaEspecialista);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM especialista_has_area_especializacion WHERE area_id = ?1 AND especialista_cedula = ?2", nativeQuery = true)
    void deleteAreaEspecialista(int areaId, String cedulaEspecialista);

    @Query(value = "SELECT * FROM especialista_has_area_especializacion WHERE area_id = ?1 AND especialista_cedula = ?2", nativeQuery = true)
    List<Object> encontrarAreaConEspecialista(int id, String especialistaCedula);
}
