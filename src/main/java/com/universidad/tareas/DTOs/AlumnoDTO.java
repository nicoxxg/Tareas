package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.*;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AlumnoDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private Perfiles perfiles;

    private boolean suspendido;
    private Set<InscripcionDTO> inscripciones;
    private Set<AlumnoTareaDTO> tareaEntregada;
    private Set<TareaDTO> tareasPendientes;



    public AlumnoDTO(Alumno alumno) {
        this.id = alumno.getId();
        this.nombre = alumno.getNombre();
        this.apellido = alumno.getApellido();
        this.email = alumno.getEmail();
        this.contraseña = alumno.getContraseña();
        this.perfiles = alumno.getPerfiles();
        this.suspendido = alumno.isSuspendido();
        this.inscripciones = alumno.getInscripciones().stream().filter(inscripcion -> inscripcion.isActivo())
                .map((inscripcion) -> new InscripcionDTO(inscripcion)).collect(Collectors.toSet());
        this.tareaEntregada = alumno.getEntregas().stream()
                .map(entrega -> new AlumnoTareaDTO(entrega.getTarea(),entrega)).collect(Collectors.toSet());
        this.tareasPendientes = calcularTareasPendientes(alumno);

    }

    private Set<TareaDTO> calcularTareasPendientes(Alumno alumno) {
        Set<TareaDTO> tareasPendientes = new HashSet<>();

        Set<Tarea> tareasActivas = alumno.getInscripciones().stream()
                .filter(Inscripcion::isActivo)
                .map(Inscripcion::getCurso)
                .flatMap(curso -> curso.getTareas().stream())
                .collect(Collectors.toSet());

        for (Tarea tarea : tareasActivas) {
            boolean entregada = alumno.getEntregas().stream()
                    .anyMatch(entrega -> entrega.getTarea().equals(tarea));

            if (!entregada) {
                tareasPendientes.add(new TareaDTO(tarea));
            }
        }

        return tareasPendientes;
    }

    public Set<AlumnoTareaDTO> getTareaEntregada() {
        return tareaEntregada;
    }

    public boolean isSuspendido() {
        return suspendido;
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

    public Set<InscripcionDTO> getInscripciones() {
        return inscripciones;
    }

    public Set<TareaDTO> getTareasPendientes() {
        return tareasPendientes;
    }
}
