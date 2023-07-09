package com.universidad.tareas.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String nombreAlumno;

    private Nota nota;
    private EstadoTarea estadoTarea;
    @Lob
    private byte[] archivo;
    private String nombreArchivo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Tarea tarea;
    @ManyToOne(fetch = FetchType.EAGER)
    private Alumno alumno;

    public Entrega() {
    }

    public Entrega(String nombreAlumno, Nota nota, EstadoTarea estadoTarea, byte[] archivo, String nombreArchivo, Tarea tarea, Alumno alumno) {
        this.nombreAlumno = nombreAlumno;
        this.nota = nota;
        this.estadoTarea = estadoTarea;
        this.archivo = archivo;
        this.nombreArchivo = nombreArchivo;
        this.tarea = tarea;
        this.alumno = alumno;
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

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public EstadoTarea getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(EstadoTarea estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
