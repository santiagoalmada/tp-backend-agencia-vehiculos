package com.tpi.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Nuestro archivo de configuración de rutas para el Gateway
// Aquí definimos las rutas que redirigirán las peticiones a los microservicios correspondientes
// Redirigimos las peticiones que lleguen a /api/pruebas/** al microservicio de pruebas (puerto 8083)

// Ejemplo: http://localhost:8082/api/pruebas/empleados --> http://localhost:8083/empleados

@Configuration
public class GWConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruteo basado en la ruta "/api/pruebas/**"
                .route(r -> r.path("/api/pruebas/**")
                        .uri("http://localhost:8083")) // Redirige al microservicio de pruebas


                // Ruteo basado en la ruta "/api/notificaciones/**"
                .route(r -> r.path("/api/notificaciones/**")
                        .uri("http://localhost:8084")) // Redirige al microservicio de notificaciones

                .build();
    }
}
