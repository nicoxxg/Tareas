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
			
		};
	}

}
