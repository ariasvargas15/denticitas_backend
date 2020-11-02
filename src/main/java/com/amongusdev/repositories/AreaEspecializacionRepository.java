package com.amongusdev.repositories;

import com.amongusdev.models.AreaEspecializacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaEspecializacionRepository extends JpaRepository<AreaEspecializacion, Integer> {
}
