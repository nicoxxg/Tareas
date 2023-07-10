package com.universidad.tareas.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                //.addResourceLocations("C:\\Users\\erick\\OneDrive\\Escritorio\\tareas\\static") // Reemplaza con la ruta absoluta a la carpeta "static"
                .setCachePeriod(0); // Desactivar el caché durante el desarrollo
    }

}
