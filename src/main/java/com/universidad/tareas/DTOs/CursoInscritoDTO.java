package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Curso;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.models.TurnoClase;

import java.util.Set;
import java.util.stream.Collectors;

public class CursoInscritoDTO {
    private long id;
    private String numeroGrado;
    private String descripcion;
    private TurnoClase turno;
    private Profesor profesor;
    private Set<TareaDTO> tarea;

    public CursoInscritoDTO(Curso curso) {
        this.id = curso.getId();
        this.numeroGrado = curso.getNumeroGrado();
        this.descripcion = curso.getDescripcion();
        this.turno = curso.getTurno();
        this.profesor = curso.getProfesor();
        this.tarea = curso.getTareas().stream().map(tarea1 -> new TareaDTO(tarea1)).collect(Collectors.toSet());
    }

    public Set<TareaDTO> getTarea() {
        return tarea;
    }

    public long getId() {
        return id;
    }

    public String getNumeroGrado() {
        return numeroGrado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TurnoClase getTurno() {
        return turno;
    }

    public Profesor getProfesor() {
        return profesor;
    }
}
