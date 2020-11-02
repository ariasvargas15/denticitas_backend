package com.amongusdev.repositories;

import com.amongusdev.models.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialistaRepository extends JpaRepository<Especialista, String> {
}
