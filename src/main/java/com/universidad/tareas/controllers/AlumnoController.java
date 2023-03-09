package com.universidad.tareas.controllers;

import com.universidad.tareas.DTOs.AlumnoDTO;
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
public class AlumnoController {
    @Autowired
    public ProfesorRepository profesorRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    AlumnoRepository alumnoRepository;

    @GetMapping("/alumno")//
    public List<AlumnoDTO> getAllAlumno(){
        return alumnoRepository.findAll().stream().map((alumno) -> new AlumnoDTO(alumno)).collect(Collectors.toList());
    }
    @PatchMapping("/alumno/current/actualizar")//
    public Alumno actualizarAlumno(@RequestParam String nuevoNombre,
                                   @RequestParam String nuevoApellido, @RequestParam String nuevaContraseoña,
                                    Authentication authentication){

        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        if (nuevoNombre ==  null){
            nuevoNombre = alumno.getNombre();
        }
        if (nuevoApellido == null){
            nuevoApellido = alumno.getApellido();
        }
        if (nuevaContraseoña == null){
            nuevaContraseoña = alumno.getContraseña();
        }

        alumno.setNombre(nuevoNombre);
        alumno.setApellido(nuevoApellido);
        alumno.setContraseña(passwordEncoder.encode(nuevaContraseoña));

        return alumnoRepository.save(alumno);
    }

    @PostMapping("/alumno/create")//
    public ResponseEntity<Object> createAlumno(@RequestParam String nombre,
                                               @RequestParam String apellido,
                                               @RequestParam String email,
                                               @RequestParam String contraseña){
        Profesor profesor = profesorRepository.findByEmail(email);

        if (nombre == null || apellido == null || email == null || contraseña == null){

            return  new ResponseEntity<>("falta informcion",HttpStatus.FORBIDDEN);

        }
        if (profesor != null){
            return new ResponseEntity<>("ya hay un profesor con ese email", HttpStatus.FORBIDDEN);
        }

        Alumno alumno = new Alumno(nombre,apellido,email, passwordEncoder.encode(contraseña), Perfiles.Alumno,false);

        alumnoRepository.save(alumno);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/alumno/{id}")
    public AlumnoDTO obtenerAlumnoPorId(@PathVariable long id){
        return new AlumnoDTO(alumnoRepository.findById(id).orElse(null));
    }
    @PatchMapping("/alumno/{id}/suspender")
    public ResponseEntity<Object> suspenderAlumno(@PathVariable long id){
        Alumno alumno = alumnoRepository.getById(id);
        if (alumno == null){
            return new ResponseEntity<>("el alumno no existe",HttpStatus.FORBIDDEN);
        }
        if (alumno.isSuspendido() == true) {
            alumno.setSuspendido(false);
        } else if (alumno.isSuspendido() == false){
            alumno.setSuspendido(true);
        }

        alumnoRepository.save(alumno);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
