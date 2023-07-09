package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Tarea;

import java.util.Objects;

public class TareaKeyDTO {
    private long id;
    private String nombreTarea;

    public TareaKeyDTO(Tarea tarea) {
        this.id = tarea.getId();
        this.nombreTarea = tarea.getNombreTarea();
    }

    public long getId() {
        return id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

}
