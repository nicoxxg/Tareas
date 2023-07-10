package com.universidad.tareas.controllers;

import com.universidad.tareas.DTOs.TareaDTO;
import com.universidad.tareas.models.Curso;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.models.Tarea;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.CursoRepository;
import com.universidad.tareas.repository.ProfesorRepository;
import com.universidad.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/api")
public class TareaController {
    @Autowired
    TareaRepository tareaRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    CursoRepository cursoRepository;
    @GetMapping("/tarea/{idTarea}")
    private ResponseEntity<TareaDTO> getTareaById(@PathVariable long idTarea){
        return new ResponseEntity<>(new TareaDTO(tareaRepository.findById(idTarea)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro la tarea"))),HttpStatus.ACCEPTED);
    }

    @PostMapping("/tarea")
    private ResponseEntity<Object> crearNuevaTarea(Authentication authentication, @RequestParam String nombreTarea,@RequestParam String descripcionTarea,@RequestParam long idCurso){

        if (nombreTarea.isEmpty() || descripcionTarea.isEmpty() || idCurso == 0){
            return new ResponseEntity<>("uno de estos parametros esta vacio",HttpStatus.FORBIDDEN);
        }
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("curso no encontrado"));
        Profesor profesor = profesorRepository.findByEmail(authentication.getName());

        if (!curso.getProfesor().equals(profesor)){
            return new ResponseEntity<>("el curso que selecciono no le pertenece",HttpStatus.FORBIDDEN);
        }

        Tarea tarea = new Tarea(nombreTarea,descripcionTarea,curso);

        tareaRepository.save(tarea);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
