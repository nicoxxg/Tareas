package com.universidad.tareas;

import com.universidad.tareas.models.*;
import com.universidad.tareas.repository.*;
import com.universidad.tareas.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TareasApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(AlumnoRepository alumnoRepository,
									  ProfesorRepository profesorRepository,
									  InscripcionRepository inscripcionRepository,
									  CursoRepository cursoRepository,
									  EntregaRepository entregaRepository,
									  TareaRepository tareaRepository){
		return args -> {
			// Crear profesor
			Profesor admin = new Profesor("pepe", "pepe", "admin", passwordEncoder.encode("admin"), Perfiles.Administrador);
			profesorRepository.save(admin);

			// Crear curso
			Curso curso = new Curso("er", "er", TurnoClase.Mañana, admin);
			cursoRepository.save(curso);

			Curso curso1 = new Curso("6", "pepepe", TurnoClase.Mañana, admin);
			cursoRepository.save(curso1);

			// Crear alumnos
			Alumno alumno1 = new Alumno("John", "Doe", "john.doe@example.com", passwordEncoder.encode("password"), Perfiles.Alumno, false);
			Alumno alumno2 = new Alumno("Jane", "Smith", "jane.smith@example.com", passwordEncoder.encode("password"), Perfiles.Alumno, false);
			alumnoRepository.save(alumno1);
			alumnoRepository.save(alumno2);

			// Crear inscripciones
			Inscripcion inscripcion1 = new Inscripcion("John Doe", true, alumno1, curso);
			Inscripcion inscripcion2 = new Inscripcion("Jane Smith", true, alumno2, curso);
			inscripcionRepository.save(inscripcion1);
			inscripcionRepository.save(inscripcion2);

			// Crear tareas
			Tarea tarea1 = new Tarea("Tarea 1", "Descripción de la Tarea 1", curso);
			Tarea tarea2 = new Tarea("Tarea 2", "Descripción de la Tarea 2", curso);
			tareaRepository.save(tarea1);
			tareaRepository.save(tarea2);

			// Crear entregas
			Entrega entrega1 = new Entrega("John Doe", Nota.Aprobado, EstadoTarea.Entregado, null, "archivo1.txt", tarea1, alumno1);
			Entrega entrega2 = new Entrega("Jane Smith", Nota.Desaprobado, EstadoTarea.Entregado, null, "archivo2.txt", tarea2, alumno2);
			entregaRepository.save(entrega1);
			entregaRepository.save(entrega2);
		};
	}
	@EventListener(ApplicationReadyEvent.class)
	public void sendEmail(){
		senderService.sendEmail("erickyma23gmail.com","subject","probando que ande");
	}

}
