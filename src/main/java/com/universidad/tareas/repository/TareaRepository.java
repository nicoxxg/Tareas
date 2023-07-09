package com.universidad.tareas.repository;

import com.universidad.tareas.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TareaRepository extends JpaRepository<Tarea,Long> {
}
