package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.*;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.stream.Collectors;

public class ProfesorDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private Perfiles perfiles;
    private Set<CursoDTO> cursosDictados;
    private Map<Long, Set<AlumnosVerificacionDTO>> verificacionesPorTarea;


    public ProfesorDTO(Profesor profesor) {
        this.id = profesor.getId();
        this.nombre = profesor.getNombre();
        this.apellido = profesor.getApellido();
        this.email = profesor.getEmail();
        this.contraseña = profesor.getContraseña();
        this.perfiles = profesor.getPerfiles();
        this.cursosDictados = profesor.getCursosDictados().stream()
                .map((curso) -> new CursoDTO(curso)).collect(Collectors.toSet());
        this.verificacionesPorTarea = obtenerVerificacionesPorTarea(profesor);
    }
    private Map<Long, Set<AlumnosVerificacionDTO>> obtenerVerificacionesPorTarea(Profesor profesor) {
        Map<Long, Set<AlumnosVerificacionDTO>> verificacionesPorTarea = new HashMap<>();
        for (Curso curso : profesor.getCursosDictados()) {
            for (Tarea tarea : curso.getTareas()) {
                Set<AlumnosVerificacionDTO> verificaciones = new HashSet<>();
                for (Entrega entrega : tarea.getEntregas()) {
                    Alumno alumno = entrega.getAlumno();
                    AlumnosVerificacionDTO alumnosVerificacionDTO = new AlumnosVerificacionDTO(alumno, entrega);
                    verificaciones.add(alumnosVerificacionDTO);
                }
                verificacionesPorTarea.put(tarea.getId(), verificaciones);
            }
        }
        return verificacionesPorTarea;
    }

    public Map<Long, Set<AlumnosVerificacionDTO>> getVerificacionesPorTarea() {
        return verificacionesPorTarea;
    }

    public Perfiles getPerfiles() {
        return perfiles;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Set<CursoDTO> getCursosDictados() {
        return cursosDictados;
    }
}
