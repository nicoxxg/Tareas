package com.universidad.tareas.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String numeroGrado;
    private String descripcion;
    private TurnoClase turno;

    @OneToMany(mappedBy = "curso",fetch = FetchType.EAGER)
    private Set<Tarea> tareas;

    @OneToMany(mappedBy = "curso",fetch = FetchType.EAGER)
    private Set<Inscripcion> inscripciones = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Profesor profesor;

    public Curso() {
    }

    public Curso(String numeroGrado, String descripcion, TurnoClase turno,Profesor profesor) {
        this.numeroGrado = numeroGrado;
        this.descripcion = descripcion;
        this.turno = turno;
        this.profesor = profesor;
    }

    public long getId() {
        return id;
    }

    public String getNumeroGrado() {
        return numeroGrado;
    }

    public void setNumeroGrado(String numeroGrado) {
        this.numeroGrado = numeroGrado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TurnoClase getTurno() {
        return turno;
    }

    public void setTurno(TurnoClase turno) {
        this.turno = turno;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }
}
