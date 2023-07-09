package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.*;

public class AlumnosVerificacionDTO {
    private long idTarea;
    private long idEntrega;
    private long idAlumno;
    private EstadoTarea estado;
    private String nombreTarea;
    private String apodoAlumno;
    private String nombreAlumno;
    private String apellidoAlumno;
    private String emailAlumno;
    private String nombreArchivo;
    private Nota nota;



    public AlumnosVerificacionDTO(Alumno alumno, Entrega entrega) {
        this.idTarea = entrega.getTarea().getId();
        this.idEntrega = entrega.getId();
        this.idAlumno = alumno.getId();
        this.nombreTarea = entrega.getTarea().getNombreTarea();
        this.nombreAlumno = alumno.getNombre();
        this.apodoAlumno = entrega.getNombreAlumno();
        this.apellidoAlumno = alumno.getApellido();
        this.estado = entrega.getEstadoTarea();
        this.emailAlumno = alumno.getEmail();
        this.nombreArchivo = entrega.getNombreArchivo();
        this.nota = entrega.getNota();

    }

    public Nota getNota() {
        return nota;
    }

    public long getIdEntrega() {
        return idEntrega;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public long getIdTarea() {
        return idTarea;
    }

    public long getIdAlumno() {
        return idAlumno;
    }

    public String getApodoAlumno() {
        return apodoAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public String getApellidoAlumno() {
        return apellidoAlumno;
    }

    public String getEmailAlumno() {
        return emailAlumno;
    }


    public String getNombreArchivo() {
        return nombreArchivo;
    }
}
