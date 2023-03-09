package com.universidad.tareas.controllers;

import com.universidad.tareas.DTOs.AlumnoDTO;
import com.universidad.tareas.DTOs.ProfesorDTO;
import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.ProfesorRepository;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrentController {
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    AlumnoRepository alumnoRepository;
    @GetMapping("/current")//
    public Object getCurrent(Authentication authentication){

        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch("Profesor"::equals)){
            Profesor profesor = profesorRepository.findByEmail(authentication.getName());
            return new ProfesorDTO(profesor);
        }
        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch("Admin"::equals)){
            Profesor profesor = profesorRepository.findByEmail(authentication.getName());
            return new ProfesorDTO(profesor);
        }
        if (authorities.stream().map(GrantedAuthority::getAuthority).anyMatch("Alumno"::equals)){
            Alumno alumno = alumnoRepository.findByEmail(authentication.getName());
            System.out.println(alumno.toString());
            return new AlumnoDTO(alumno);
        }

        return null;
    }
}
