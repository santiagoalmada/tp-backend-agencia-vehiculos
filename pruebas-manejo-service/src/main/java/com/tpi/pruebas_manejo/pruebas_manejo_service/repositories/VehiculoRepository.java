package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas
}