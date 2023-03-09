package com.universidad.tareas.controllers;

import com.universidad.tareas.DTOs.CursoDTO;
import com.universidad.tareas.DTOs.InscripcionDTO;
import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Curso;
import com.universidad.tareas.models.Inscripcion;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.CursoRepository;
import com.universidad.tareas.repository.InscripcionRepository;
import com.universidad.tareas.repository.ProfesorRepository;
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
public class InscripcionController {
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    InscripcionRepository inscripcionRepository;
    @Autowired
    CursoRepository cursoRepository;

    @PostMapping("/Inscripcion/create")//
    private ResponseEntity<Object> createInscripcion(Authentication authentication, @RequestParam String apodo,@RequestParam long idCurso){
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        Curso curso = cursoRepository.getById(idCurso);
        List<Inscripcion> inscripcionesDesactivadas = alumno.getInscripciones().stream().filter(inscripcion -> inscripcion.isActivo() == true).collect(Collectors.toList());
        List<Inscripcion> inscripcionesRepetidas = inscripcionesDesactivadas.stream().filter(inscripcion -> inscripcion.getCurso().getId() == curso.getId()).collect(Collectors.toList());

        if (inscripcionesRepetidas.size() != 0 ){
            return new ResponseEntity<>("ya estas inscrito en esta clase",HttpStatus.FORBIDDEN);
        }
        if (curso == null){
            return new ResponseEntity<>("no se ah encontrado el curso",HttpStatus.FORBIDDEN);
        }
        if (alumno == null){
            return new ResponseEntity<>("no hay un alumno conectado",HttpStatus.FORBIDDEN);
        }
        Inscripcion inscripcion = new Inscripcion(apodo,true,alumno,curso);
        inscripcionRepository.save(inscripcion);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/current/inscripcion/{id}/actualizar")//
    public ResponseEntity<Object> cambiarApodo(@PathVariable long id,@RequestParam String nuevoNombre,Authentication authentication){
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        Inscripcion inscripcion = inscripcionRepository.getById(id);
        List<Inscripcion> inscriocionCliente = alumno.getInscripciones().stream().filter(inscripcion1 -> inscripcion1 == inscripcion).collect(Collectors.toList());

        if (inscriocionCliente == null){
            return  new ResponseEntity<>("esta inscripocion no le pertenece al alumno",HttpStatus.FORBIDDEN);
        }
        inscripcion.setNombreAlumno(nuevoNombre);
        inscripcionRepository.save(inscripcion);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PatchMapping("/current/inscripcion/{id}/actualizar/apodo")//
    public ResponseEntity<Object> borrarInscripcion(@PathVariable long id,Authentication authentication){
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        Inscripcion inscripcion = inscripcionRepository.getById(id);
        List<Inscripcion> inscriocionCliente = alumno.getInscripciones().stream().filter(inscripcion1 -> inscripcion1.getId() == inscripcion.getId()).collect(Collectors.toList());

        if (inscriocionCliente == null){
            return  new ResponseEntity<>("esta inscripocion no le pertenece al alumno",HttpStatus.FORBIDDEN);
        }
        inscripcion.setActivo(false);
        inscripcionRepository.save(inscripcion);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PatchMapping("/alumno/{idAlumno}/inscripcion/{id}/actualizar")//
    public ResponseEntity<Object> borrarInscripcionPorProfesor(@PathVariable long id,@PathVariable long idAlumno){
        Alumno alumno = alumnoRepository.getById(idAlumno);
        Inscripcion inscripcion = inscripcionRepository.getById(id);
        List<Inscripcion> inscriocionCliente = alumno.getInscripciones().stream().filter(inscripcion1 -> inscripcion1.getId() == inscripcion.getId()).collect(Collectors.toList());

        if (inscriocionCliente == null){
            return  new ResponseEntity<>("esta inscripocion no le pertenece al alumno",HttpStatus.FORBIDDEN);
        }
        inscripcion.setActivo(false);
        inscripcionRepository.save(inscripcion);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/current/inscripcion/{id}")//
    public Object obtenerCursoDelAlumno(Authentication authentication ,@PathVariable long id){
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        if (!authorities.stream().map(GrantedAuthority::getAuthority).anyMatch("Alumno"::equals)){
            return new ResponseEntity<>("no es un alumno",HttpStatus.FORBIDDEN);
        }
        Inscripcion curso = inscripcionRepository.getById(id);
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        List<Inscripcion> listaCurso = alumno.getInscripciones().stream().filter(inscripcion -> inscripcion == curso).collect(Collectors.toList());

        if (curso == null){
            return new ResponseEntity<>("el curso no existe",HttpStatus.FORBIDDEN);
        }
        if (listaCurso == null){
            return new ResponseEntity<>("el curso que estas pidiendo no estas inscrito",HttpStatus.FORBIDDEN);
        }


        return new InscripcionDTO(curso);
    }
    @GetMapping("/current/inscripcion")//
    public List<InscripcionDTO> obtenerTodosLosInscripciones(Authentication authentication){
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        List<Inscripcion> inscripcionesActivas = alumno.getInscripciones().stream().filter(Inscripcion::isActivo).collect(Collectors.toList());
        return inscripcionesActivas.stream().map(curso -> new InscripcionDTO(curso)).collect(Collectors.toList());
    }



}
