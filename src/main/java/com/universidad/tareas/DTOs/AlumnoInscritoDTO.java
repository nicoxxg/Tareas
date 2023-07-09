package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Alumno;

import java.util.Set;
import java.util.stream.Collectors;

public class AlumnoInscritoDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;

    public AlumnoInscritoDTO(Alumno alumno) {
        this.id = alumno.getId();
        this.nombre = alumno.getNombre();
        this.apellido = alumno.getApellido();
        this.email = alumno.getEmail();

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


}
