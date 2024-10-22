package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Interesado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteresadoRepository extends JpaRepository<Interesado, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas
}