package com.universidad.tareas.configurations;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.WebAttributes;

import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration

class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override //sobre escribir

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/Profesor/**").hasAuthority("Profesor")
                .antMatchers("/Alumno/**").hasAuthority("Alumno")
                .antMatchers("/Administrador/**").hasAuthority("Admin")
                .antMatchers(HttpMethod.PATCH,"/api/profesor/current/actualizar","/api/curso/{idCurso}/actualizar").hasAnyAuthority("Admin","Profesor")
                .antMatchers(HttpMethod.POST,"/api/Inscripcion/create").hasAuthority("Alumno")
                .antMatchers(HttpMethod.PATCH,"/api/alumno/{idAlumno}/inscripcion/{id}/actualizar","/api/alumno/{id}/suspender").hasAnyAuthority("Profesor","Admin")
                .antMatchers(HttpMethod.PATCH,"/api/alumno/current/actualizar","/api/alumno/current/actualizar","/api/current/inscripcion/{id}/actualizar/apodo").hasAuthority("Alumno")
                .antMatchers(HttpMethod.POST,"/api/curso/create","/api/current/inscripcion/{id}").hasAnyAuthority("Admin","Profesor")
                .antMatchers("/api/current/inscripcion").hasAuthority("Alumno")
                .antMatchers("/api/current/**").hasAnyAuthority("Admin","Alumno","Profesor")
                .antMatchers("/api/Inscripcion/create","/api/current/inscripcion/{id}","/api/alumno","/api/alumno/{id}").hasAnyAuthority("Profesor","Admin")
                .antMatchers("/api/curso","/api/current","/api/curso/{id}").hasAnyAuthority("Admin","Profesor","Alumno")
                .antMatchers("/api/alumno/create","/api/profesor/create").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/curso/cambiar/profesor").hasAuthority("Admin")
                .antMatchers("/admin/**","/rest/**","/h2-console","/api/**","/api/profesor").hasAuthority("Admin");

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("contraseña")

                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }




}
