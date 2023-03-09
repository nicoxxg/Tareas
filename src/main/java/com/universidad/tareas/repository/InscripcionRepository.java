package com.universidad.tareas.repository;

import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}
