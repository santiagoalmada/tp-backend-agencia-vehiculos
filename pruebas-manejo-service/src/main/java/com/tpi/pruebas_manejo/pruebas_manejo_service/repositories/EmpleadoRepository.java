package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Buscar los empleados cuyo apellido empiece con D:
    List<Empleado> findByApellidoStartingWith(String apellido);

    // JPA se encarga de implementar la consulta por vos:
    // SELECT * FROM empleado WHERE apellido LIKE 'D%';
    // Para ver más ejemplos de consultas soportadas por JPA: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
}