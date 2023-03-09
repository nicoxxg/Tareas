package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Curso;
import com.universidad.tareas.models.Perfiles;
import com.universidad.tareas.models.Profesor;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfesorDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private Perfiles perfiles;
    private Set<CursoDTO> cursosDictados;

    public ProfesorDTO(Profesor profesor) {
        this.id = profesor.getId();
        this.nombre = profesor.getNombre();
        this.apellido = profesor.getApellido();
        this.email = profesor.getEmail();
        this.contraseña = profesor.getContraseña();
        this.perfiles = profesor.getPerfiles();
        this.cursosDictados = profesor.getCursosDictados().stream().map((curso) -> new CursoDTO(curso)).collect(Collectors.toSet());
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
