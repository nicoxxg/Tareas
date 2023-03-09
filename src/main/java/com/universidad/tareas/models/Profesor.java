package com.universidad.tareas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;

    private Perfiles perfiles;
    @OneToMany(mappedBy = "profesor",fetch = FetchType.EAGER)
    private Set<Curso> cursosDictados;

    public Profesor() {
    }


    public Profesor(String nombre, String apellido, String email,String contraseña,Perfiles perfiles) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.perfiles = perfiles;
    }

    public Perfiles getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(Perfiles perfiles) {
        this.perfiles = perfiles;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public Set<Curso> getCursosDictados() {
        return cursosDictados;
    }

    @JsonIgnore
    public void setCursosDictados(Set<Curso> cursosDictados) {
        this.cursosDictados = cursosDictados;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
