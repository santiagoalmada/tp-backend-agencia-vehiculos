package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReporteKilometrosDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PosicionRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportesService {
    @Autowired
    private PosicionRepository posicionRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public ReporteKilometrosDTO generarReporteKilometrosRecorridos(Long vehiculoId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        // Obtener el vehiculo por su ID
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).
                orElseThrow(() -> new RuntimeException("Vehículo no encontrado."));

        // Obtener las posiciones del vehículo entre las fechas indicadas
        List<Posicion> posicionesDelVehiculo = posicionRepository.findPosicionesEntreFechasPorVehiculo(fechaInicio, fechaFin, vehiculoId);

        if (posicionesDelVehiculo.isEmpty()) {
            throw new RuntimeException("No se encontraron posiciones para el vehículo en el rango de fechas indicado.");
        }


        // Calcular la distancia recorrida (usando posicion.calcularDistancia(otraPosicion) )
        double distanciaRecorrida = 0;
        for (int i = 0; i < posicionesDelVehiculo.size() - 1; i++) {
            Posicion posicionActual = posicionesDelVehiculo.get(i);
            Posicion posicionSiguiente = posicionesDelVehiculo.get(i + 1);
            distanciaRecorrida += posicionActual.calcularDistancia(posicionSiguiente);
        }

        // Formateo del reporte
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n===============================================\n");
        reporte.append("Reporte de Kilómetros Recorridos\n");
        reporte.append("===============================================\n");
        reporte.append(String.format("Vehículo ID: %d\n", vehiculoId));
        reporte.append(String.format("Fecha de inicio: %s\n", fechaInicio));
        reporte.append(String.format("Fecha de fin: %s\n", fechaFin));
        reporte.append("-----------------------------------------------\n");
        reporte.append(String.format("Distancia total recorrida: %.2f km\n", distanciaRecorrida));
        reporte.append("===============================================\n");

        System.out.printf(reporte.toString());

        return new ReporteKilometrosDTO(vehiculoId, fechaInicio.toString(), fechaFin.toString(), vehiculo.getPatente(), distanciaRecorrida);
    };

}
