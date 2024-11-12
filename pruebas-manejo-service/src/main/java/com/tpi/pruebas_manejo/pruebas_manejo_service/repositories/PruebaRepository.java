package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Cada repositorio extiende de JpaRepository,
// lo cual te proporciona acceso a métodos CRUD predefinidos como save(), findAll(), findById(), etc.

// Para ver más ejemplos de consultas soportadas por JPA: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

public interface PruebaRepository extends JpaRepository<Prueba, Long> {
    // Pruebas en curso (fechaHoraFin es null)
    List<Prueba> findByFechaHoraFinIsNull();

    // Pruebas que excedio limite de la agencia o entró en zona prohibida.
    List<Prueba> findByExcedioLimiteIsTrue();

    // Pruebas de un empleado que excedieron el limite
    List<Prueba> findByExcedioLimiteIsTrueAndEmpleado_Legajo(Long legajo);

    // Pruebas de un vehiculo
    List<Prueba> findByVehiculo_Id(Long vehiculoId);
}

