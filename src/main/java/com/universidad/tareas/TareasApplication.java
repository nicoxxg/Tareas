package com.universidad.tareas;

import com.universidad.tareas.models.*;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.CursoRepository;
import com.universidad.tareas.repository.InscripcionRepository;
import com.universidad.tareas.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TareasApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(AlumnoRepository alumnoRepository,
									  ProfesorRepository profesorRepository,
									  InscripcionRepository inscripcionRepository,
									  CursoRepository cursoRepository){
		return args -> {
			Alumno alumno1 = new Alumno("erick","barraza","erickyma23@gmail.com", passwordEncoder.encode("erick012"), Perfiles.Alumno,false);
			alumnoRepository.save(alumno1);
			Profesor profesor1 = new Profesor("rodrigo","el chico","admin@gmail.com", passwordEncoder.encode("hola"),Perfiles.Administrador);
			profesorRepository.save(profesor1);
			Curso cursoArte = new Curso("6to arte","este es el curso de arte de sexto año, haciendolon su" +
					" ultimo año en el que liberaran su creatividad", TurnoClase.Mañana,profesor1);
			cursoRepository.save(cursoArte);
			Inscripcion inscripcionArte = new Inscripcion(alumno1.getNombreCompleto(),true, alumno1,cursoArte);
			inscripcionRepository.save(inscripcionArte);

		};
	}

}
