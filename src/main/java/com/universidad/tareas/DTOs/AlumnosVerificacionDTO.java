package com.universidad.tareas.DTOs;

import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Entrega;
import com.universidad.tareas.models.EstadoTarea;
import com.universidad.tareas.models.Inscripcion;

public class AlumnosVerificacionDTO {
    private long idTarea;
    private long idAlumno;
    private EstadoTarea estado;
    private String nombreTarea;
    private String apodoAlumno;
    private String nombreAlumno;
    private String apellidoAlumno;
    private String emailAlumno;
    private byte[] archivo;
    private String nombreArchivo;



    public AlumnosVerificacionDTO(Alumno alumno, Entrega entrega) {
        this.idTarea = entrega.getTarea().getId();
        this.idAlumno = alumno.getId();
        this.nombreTarea = entrega.getTarea().getNombreTarea();
        this.nombreAlumno = alumno.getNombre();
        this.apodoAlumno =alumno.getInscripciones().stream()
                .filter(inscripcion -> inscripcion.getCurso().equals(entrega.getTarea().getCurso()))
                .findFirst()
                .map(Inscripcion::getNombreAlumno)
                .orElse(null);
        this.apellidoAlumno = alumno.getApellido();
        this.estado = entrega.getEstadoTarea();
        this.emailAlumno = alumno.getEmail();
        this.archivo = entrega.getArchivo();
        this.nombreArchivo = entrega.getNombreArchivo();

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

    public byte[] getArchivo() {
        return archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }
}
