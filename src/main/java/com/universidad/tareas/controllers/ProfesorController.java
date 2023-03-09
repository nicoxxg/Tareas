package com.universidad.tareas.controllers;

import com.universidad.tareas.DTOs.ProfesorDTO;
import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Perfiles;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfesorController {
    @Autowired
    public AlumnoRepository alumnoRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    ProfesorRepository profesorRepository;
    @GetMapping("/profesor")//
    public List<ProfesorDTO> getAllProfesor(){
        return profesorRepository.findAll().stream().map((profesor) -> new ProfesorDTO(profesor)).collect(Collectors.toList());
    }
    @PostMapping("/profesor/create")//
    public ResponseEntity<Object> CreateProfesor(@RequestParam String nombre,
                                                 @RequestParam String apellido,
                                                 @RequestParam String email,
                                                 @RequestParam String contraseña){

        Alumno alumno = alumnoRepository.findByEmail(email);
        if (nombre == null || apellido == null || email == null || contraseña == null){
            return  new ResponseEntity<>("falta informcion",HttpStatus.FORBIDDEN);
        }
        if (email.contains("@admin")){
            return new ResponseEntity<>("no puedes registrarte como admin", HttpStatus.FORBIDDEN);
        }
        if (alumno != null){
            return new ResponseEntity<>("ya hay un alumno con ese email", HttpStatus.FORBIDDEN);
        }
        if (profesorRepository.findByEmail(email) != null){
            return new ResponseEntity<>("el email que intentas usar ya esta en uso", HttpStatus.FORBIDDEN);
        }
        Profesor profesor = new Profesor(nombre,apellido,email, passwordEncoder.encode(contraseña), Perfiles.Profesor);
        profesorRepository.save(profesor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/profesor/current/actualizar")//
    public Profesor actualizarProfesor(@RequestParam String nuevoNombre,
                                   @RequestParam String nuevoApellido, @RequestParam String nuevaContraseoña,
                                    Authentication authentication){

        Profesor profesor = profesorRepository.findByEmail(authentication.getName());
        if (nuevoNombre ==  null){
            nuevoNombre = profesor.getNombre();
        }
        if (nuevoApellido == null){
            nuevoApellido = profesor.getApellido();
        }
        if (nuevaContraseoña == null){
            nuevaContraseoña = profesor.getContraseña();
        }

        profesor.setNombre(nuevoNombre);
        profesor.setApellido(nuevoApellido);
        profesor.setContraseña(passwordEncoder.encode(nuevaContraseoña));

        return profesorRepository.save(profesor);
    }
}
