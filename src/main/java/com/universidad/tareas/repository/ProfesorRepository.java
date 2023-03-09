package com.universidad.tareas.repository;

import com.universidad.tareas.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Profesor findByEmail(String email);
}
