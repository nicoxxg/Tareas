package com.universidad.tareas.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String nombreTarea;
    private String descripcionTarea;
    @ManyToOne(fetch = FetchType.EAGER)
    private Curso curso;
    @OneToMany(mappedBy = "tarea",fetch = FetchType.EAGER)
    private Set<Entrega> entregas;

    public Tarea() {
    }

    public Tarea(String nombreTarea, String descripcionTarea, Curso curso) {
        this.nombreTarea = nombreTarea;
        this.descripcionTarea = descripcionTarea;
        this.curso = curso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(Set<Entrega> entregas) {
        this.entregas = entregas;
    }

    public long getId() {
        return id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }
}
