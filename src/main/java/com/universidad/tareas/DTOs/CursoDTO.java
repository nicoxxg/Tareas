package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Curso;
import com.universidad.tareas.models.Inscripcion;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.models.TurnoClase;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CursoDTO {
    private long id;
    private String numeroGrado;
    private String descripcion;
    private TurnoClase turno;
    private Set<InscripcionDTO> inscripciones;
    private Set<TareaDTO> tareas;
    private ProfesorInformacionDTO profesor;

    private Set<AlumnoInscritoDTO> alumno;
    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.numeroGrado = curso.getNumeroGrado();
        this.descripcion = curso.getDescripcion();
        this.turno = curso.getTurno();

        this.tareas = curso.getTareas().stream()
                .map(tarea -> new TareaDTO(tarea)).collect(Collectors.toSet());
        this.inscripciones = curso.getInscripciones().stream()
                .filter(inscripcion -> !inscripcion.getAlumno().isSuspendido())
                .filter(inscripcion -> inscripcion.isActivo())
                .map((inscripcion) -> new InscripcionDTO(inscripcion)).collect(Collectors.toSet());
        this.tareas = curso.getTareas().stream()
                .map(tarea -> new TareaDTO(tarea)).collect(Collectors.toSet());
        this.profesor = new ProfesorInformacionDTO(curso.getProfesor());

    }

    public ProfesorInformacionDTO getProfesor() {
        return profesor;
    }

    public Set<TareaDTO> getTareas() {
        return tareas;
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

    public Set<InscripcionDTO> getInscripciones() {
        return inscripciones;
    };
}
