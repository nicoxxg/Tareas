package com.universidad.tareas.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String nombreAlumno;
    private boolean activo;


    @ManyToOne(fetch = FetchType.EAGER)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    private Curso curso;

    public Inscripcion() {
    }

    public Inscripcion(String nombreAlumno, boolean activo, Alumno alumno, Curso curso) {
        this.nombreAlumno = nombreAlumno;
        this.alumno = alumno;
        this.curso = curso;
        this.activo = activo;
    }

    public long getId() {
        return id;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
