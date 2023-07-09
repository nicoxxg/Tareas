package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Tarea;

import java.util.Set;

public class VerificacionDTO {
    private Long id;
    private String nombreTarea;
    private int cantidadVerificaciones;

    public VerificacionDTO(long id, String nombreTarea, int cantidadVerificaciones) {
        this.id = id;
        this.nombreTarea = nombreTarea;
        this.cantidadVerificaciones = cantidadVerificaciones;
    }
    public Long getId() {
        return id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public int getCantidadVerificaciones() {
        return cantidadVerificaciones;
    }
}
