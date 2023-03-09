package com.universidad.tareas.repository;

import com.universidad.tareas.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Alumno findByEmail(String email);
}
