package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas
}

