package com.universidad.tareas.controllers;

import ch.qos.logback.core.net.server.Client;
import com.universidad.tareas.DTOs.CursoDTO;
import com.universidad.tareas.models.*;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.CursoRepository;
import com.universidad.tareas.repository.InscripcionRepository;
import com.universidad.tareas.repository.ProfesorRepository;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CursoController {
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;
    @GetMapping("/curso")//
    public List<CursoDTO> getAllCurso(){
        return cursoRepository.findAll().stream().map((curso) -> new CursoDTO(curso)).collect(Collectors.toList());
    }
    @PutMapping("/curso/cambiar/profesor")//
    public ResponseEntity<Object> SustituirProfesorDeCurso(@RequestParam long idNuevoProfesor,@RequestParam long idCurso){
        Profesor profesor = profesorRepository.getById(idNuevoProfesor);
        Curso curso = cursoRepository.getById(idCurso);
        curso.setProfesor(profesor);
        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/curso/create")//
    public ResponseEntity<Object> CrearCurso(Authentication authentication, @RequestParam String numeroGrado,
                                             @RequestParam String descripcion, @RequestParam TurnoClase turno){
        Profesor profesor = profesorRepository.findByEmail(authentication.getName());

        if (profesor == null){
            return new ResponseEntity<>("no hay ningun cliente conectado",HttpStatus.FORBIDDEN);
        }
        if (numeroGrado == null || descripcion == null || turno == null){
            return new ResponseEntity<>("falta informacion",HttpStatus.FORBIDDEN);
        }
        Curso curso = new Curso(numeroGrado,descripcion,turno,profesor);
        cursoRepository.save(curso);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/current/curso")
    public List<CursoDTO> obtenercursoPorId(Authentication authentication){
        Profesor profesor = profesorRepository.findByEmail(authentication.getName());
        return profesor.getCursosDictados().stream().map(curso1 -> new CursoDTO(curso1)).collect(Collectors.toList());
    }
    @GetMapping("/curso/{id}")
    public CursoDTO obtenercursoPorId(@PathVariable long id){
        Curso curso = cursoRepository.getById(id);
        return new  CursoDTO(curso);
    }
    @PatchMapping("/curso/{idCurso}/actualizar")//
    public ResponseEntity<Object> actuaalizarCurso(@PathVariable long idCurso,Authentication authentication,
                                  @RequestParam String numeroGrado,@RequestParam String descripcion,
                                  @RequestParam TurnoClase nuevoTurnmo){
        Profesor profesor = profesorRepository.findByEmail(authentication.getName());
        Curso curso = cursoRepository.getById(idCurso);
        List<Curso> listaCurso = profesor.getCursosDictados().stream().filter(cursos -> cursos.getId() == idCurso).collect(Collectors.toList());
        if (listaCurso.size() == 0){
            return new ResponseEntity<>("el curso no le pertenece al cliente",HttpStatus.FORBIDDEN);
        }
        if (nuevoTurnmo ==  null){
            nuevoTurnmo = curso.getTurno();
        }
        if (numeroGrado == null){
            numeroGrado = curso.getNumeroGrado();
        }
        if (descripcion == null){
            descripcion = curso.getDescripcion();
        }

        curso.setDescripcion(descripcion);
        curso.setNumeroGrado(numeroGrado);
        curso.setTurno(nuevoTurnmo);

        cursoRepository.save(curso);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}
