package com.tpi.pruebas_manejo.pruebas_manejo_service.repositories;

import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PosicionRepository extends JpaRepository<Posicion, Long> {
    // Puedes agregar métodos de consulta personalizados si los necesitas

    @Query(value = "SELECT p.* FROM posiciones p " +
            "JOIN vehiculos v ON p.id_vehiculo = v.id " +
            "JOIN pruebas pr ON pr.id_vehiculo = v.id " +
            "WHERE p.fecha_hora BETWEEN :fechaInicio AND :fechaFin " +
            "AND p.id_vehiculo = :vehiculoId",
            nativeQuery = true)

    List<Posicion> findPosicionesEntreFechasPorVehiculo(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("vehiculoId") Long vehiculoId);
}