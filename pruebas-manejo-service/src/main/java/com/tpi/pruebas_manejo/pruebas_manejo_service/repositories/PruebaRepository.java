package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;


// Cada repositorio extiende de JpaRepository,
// lo cual te proporciona acceso a métodos CRUD predefinidos como save(), findAll(), findById(), etc.

public interface PruebaRepository extends JpaRepository<Prueba, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas
}
