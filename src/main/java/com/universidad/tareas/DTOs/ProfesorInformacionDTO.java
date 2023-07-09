package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Profesor;

public class ProfesorInformacionDTO {
    private String nombre;
    private String apellido;
    private String email;

    public ProfesorInformacionDTO(Profesor profesor) {
        this.nombre = profesor.getNombre();
        this.apellido = profesor.getApellido();
        this.email = profesor.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
