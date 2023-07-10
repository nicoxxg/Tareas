package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Tarea;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TareaKeyDTO {
    private long id;
    private String nombreTarea;
    private String descripcion;
    private Set<AlumnosVerificacionDTO> alumno;

    public TareaKeyDTO(Tarea tarea) {
        this.id = tarea.getId();
        this.nombreTarea = tarea.getNombreTarea();
        this.descripcion = tarea.getDescripcionTarea();
        this.alumno = tarea.getEntregas().stream().map(entrega -> new AlumnosVerificacionDTO(entrega.getAlumno(),entrega)).collect(Collectors.toSet());
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Set<AlumnosVerificacionDTO> getAlumno() {
        return alumno;
    }

    public long getId() {
        return id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

}
