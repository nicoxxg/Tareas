package com.universidad.tareas.controllers;

import ch.qos.logback.core.net.server.Client;
import com.universidad.tareas.models.*;
import com.universidad.tareas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EntregaController {

    @Autowired
    TareaRepository tareaRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    EntregaRepository entregaRepository;
    @PostMapping("/alumno/tarea/{idTarea}/entrega")
    public ResponseEntity<Object> crearNuevaEntrega(Authentication authentication,@PathVariable long idTarea,@RequestParam MultipartFile files) throws IOException {
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        Tarea tarea = tareaRepository.findById(idTarea).orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        Optional<Inscripcion> inscripcionOptional = alumno.getInscripciones().stream()
                .filter(inscripcion -> inscripcion.getCurso().equals(tarea.getCurso()))
                .findFirst();

        if (!inscripcionOptional.isPresent()) {
            return new ResponseEntity<>("El alumno no está inscrito en el curso", HttpStatus.FORBIDDEN);

        }
        if (entregaRepository.findAll().stream().anyMatch(entrega -> entrega.getAlumno().getId() == alumno.getId() && entrega.getTarea().getId() == tarea.getId())) {
            return new ResponseEntity<>("El alumno ya ha publicado una entrega para esta tarea", HttpStatus.FORBIDDEN);
        }
        Inscripcion inscripcion = inscripcionOptional.get();
        Curso curso = inscripcion.getCurso();
        String nombreAlumno = alumno.getInscripciones().stream()
                .filter(inscripcion1 -> inscripcion1.getCurso().equals(tarea.getCurso()))
                .findFirst()
                .map(inscripcion1 -> inscripcion1.getNombreAlumno())
                .orElse(null);
        Entrega entrega = new Entrega(nombreAlumno,Nota.Calificando,EstadoTarea.Entregado, files.getBytes(), files.getOriginalFilename(), tarea,alumno);
        entregaRepository.save(entrega);


        return new ResponseEntity<>("creado", HttpStatus.CREATED);
    }
    @PatchMapping("/alumno/entrega/{id}/modificar")
    public ResponseEntity<Object> modificarEntrega(Authentication authentication,@PathVariable long id,@RequestParam MultipartFile newFile) throws Exception{
        if (id == 0 || newFile.isEmpty()) {
            return new ResponseEntity<>("falta informacion", HttpStatus.FORBIDDEN);
        }
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());

        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trabajo no encontrada"));

        if (!alumno.getEntregas().stream().anyMatch(entrega1 -> entrega1.equals(entrega))) {
            return new ResponseEntity<>("El trabajo no pertenece al alumno", HttpStatus.FORBIDDEN);
        }
        entrega.setArchivo(newFile.getBytes());
        entrega.setNombreArchivo(newFile.getOriginalFilename());
        entrega.setEstadoTarea(EstadoTarea.Editado);
        entregaRepository.save(entrega);
        alumnoRepository.save(alumno);

        return new ResponseEntity<>("modificado", HttpStatus.OK);

    }

    @PatchMapping("/profesor/entrega/{idEntrega}/modificar")
    public ResponseEntity<Object> corregirEntrega(Authentication authentication,@PathVariable long idEntrega,@RequestParam Nota nota){

        Profesor profesor = profesorRepository.findByEmail(authentication.getName());
        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new IllegalArgumentException("trabajo no encontrado"));

        if (!profesor.getCursosDictados().stream().anyMatch(curso -> curso.equals(entrega.getTarea().getCurso()))) {
            return new ResponseEntity<>("El trabajo no pertenece al profesor", HttpStatus.FORBIDDEN);
        }

        entrega.setNota(nota);
        entregaRepository.save(entrega);

        return new ResponseEntity<>("corregido", HttpStatus.OK);
    }

    @GetMapping("/alumno/entrega/{entregaId}")
    public ResponseEntity<Object> descargarArchivoAlumno(@PathVariable Long entregaId,Authentication authentication) {
        Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Archivo no encontrado"));

        if (!alumno.getEntregas().stream().anyMatch(entrega1 -> entrega1.equals(entrega))){
            return new ResponseEntity<>("El trabajo no pertenece al alumno", HttpStatus.FORBIDDEN);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(entrega.getNombreArchivo()).build());

        return new ResponseEntity<>(entrega.getArchivo(), headers, HttpStatus.OK);
    }
    @GetMapping("/profesor/entrega/{entregaId}")
    public ResponseEntity<Object> descargarArchivoProfesor(@PathVariable Long entregaId,Authentication authentication) {
        Profesor profesor = profesorRepository.findByEmail(authentication.getName());
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Archivo no encontrado"));

        if (!profesor.getCursosDictados().stream().anyMatch(curso -> curso.equals(entrega.getTarea().getCurso()))){
            return new ResponseEntity<>("El trabajo no pertenece al profesor", HttpStatus.FORBIDDEN);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(entrega.getNombreArchivo()).build());

        return new ResponseEntity<>(entrega.getArchivo(), headers, HttpStatus.OK);
    }
}
