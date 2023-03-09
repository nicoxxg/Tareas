package com.universidad.tareas.configurations;

import com.universidad.tareas.models.Alumno;
import com.universidad.tareas.models.Perfiles;
import com.universidad.tareas.models.Profesor;
import com.universidad.tareas.repository.AlumnoRepository;
import com.universidad.tareas.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputEmail-> {

            Alumno client = alumnoRepository.findByEmail(inputEmail);
            Profesor profesor = profesorRepository.findByEmail(inputEmail);

            if (client != null || profesor != null) {
                if (client != null && !client.isSuspendido()){
                    return new User(client.getEmail(), client.getContraseña(),
                            AuthorityUtils.createAuthorityList("Alumno"));
                }else {
                    if (profesor.getPerfiles() == Perfiles.Administrador){
                        return new User(profesor.getEmail(), profesor.getContraseña(),
                                AuthorityUtils.createAuthorityList("Admin"));
                    } else {
                        return new User(profesor.getEmail(), profesor.getContraseña(),
                                AuthorityUtils.createAuthorityList("Profesor"));
                    }
                }
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputEmail);
            }
        });

    }
}
