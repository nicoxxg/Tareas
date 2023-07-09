package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Tarea;

public class TareaDTO {
    private long id;
    private String nombreTarea;
    private String descripcionTarea;


    public TareaDTO(Tarea tarea) {
        this.id = tarea.getId();
        this.nombreTarea = tarea.getNombreTarea();
        this.descripcionTarea = tarea.getDescripcionTarea();
    }

    public long getId() {
        return id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }
}
