package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarcaRepository extends JpaRepository<Marca, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas
}
