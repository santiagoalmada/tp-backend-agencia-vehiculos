package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicionRepository extends JpaRepository<Posicion, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas
}