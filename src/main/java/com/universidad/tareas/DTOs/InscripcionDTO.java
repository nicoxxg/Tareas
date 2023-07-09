package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Curso;
import com.universidad.tareas.models.Inscripcion;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class InscripcionDTO {
    private long id;
    private String nombreAlumno;
    private boolean activo;

    private CursoInscritoDTO curso;

    public InscripcionDTO(Inscripcion inscripcion) {
        this.id = inscripcion.getId();
        this.nombreAlumno = inscripcion.getNombreAlumno();
        this.activo = inscripcion.isActivo();
        this.curso = new CursoInscritoDTO(inscripcion.getCurso()) ;
    }

    public long getId() {
        return id;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public boolean isActivo() {
        return activo;
    }

    public CursoInscritoDTO getCurso() {
        return curso;
    }

}
