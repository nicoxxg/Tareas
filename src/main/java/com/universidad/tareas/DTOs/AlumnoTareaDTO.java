package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Entrega;
import com.universidad.tareas.models.Nota;
import com.universidad.tareas.models.Tarea;

import java.util.Set;

public class AlumnoTareaDTO {
    private long id;
    private String nombreTarea;
    private long idEntrega;
    private String descripcion;
    private Nota nota;
    private boolean entregada;
    private String nombreArchivo;

    public AlumnoTareaDTO(Tarea tareaDTO, Entrega entrega) {
        this.id = tareaDTO.getId();
        this.nombreTarea = tareaDTO.getNombreTarea();
        this.descripcion = tareaDTO.getDescripcionTarea();
        this.idEntrega = entrega.getId();
        this.nota = entrega.getNota();
        this.nombreArchivo = entrega.getNombreArchivo();
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public long getIdEntrega() {
        return idEntrega;
    }

    public Nota getNota() {
        return nota;
    }

    public long getId() {
        return id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEntregada() {
        return entregada;
    }

}
