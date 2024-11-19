package com.tpi.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange(authorize -> authorize
                        // Solo Admins pueden acceder a reportes
                        .pathMatchers("/api/pruebas/reportes/").hasRole("ADMIN")

                        // Solo empleados pueden crear/finalizar pruebas y enviar notificaciones
                        .pathMatchers("/api/pruebas/nueva").hasRole("EMPLEADO")
                        .pathMatchers("/api/pruebas/finalizar").hasRole("EMPLEADO")
                        .pathMatchers("/api/pruebas/en-curso").hasRole("EMPLEADO")

                        .pathMatchers("/api/notificaciones/**").hasRole("EMPLEADO")

                        // Solo usuarios de vehículos pueden enviar posiciones
                        .pathMatchers("/api/pruebas/posicion/actualizar").hasRole("VEHICULO")

                        // Cualquier otra petición requiere autenticación
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                return Flux.empty();
            }

            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            return Flux.fromIterable(authorities);
        });
        return converter;
    }

}