package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReporteIncidentesDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReporteKilometrosDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PosicionRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.PruebaRepository;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportesService {
    @Autowired
    private PosicionRepository posicionRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private PruebaRepository pruebaRepository;

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


    public List<ReporteIncidentesDTO> generarReporteIncidentes (Long legajoEmp) {
        List <Prueba> listadoPruebas;
        if (legajoEmp == null) {
            listadoPruebas = pruebaRepository.findByExcedioLimiteIsTrue();
        } else {
            listadoPruebas = pruebaRepository.findByExcedioLimiteIsTrueAndEmpleado_Legajo(legajoEmp);
        }

        List<ReporteIncidentesDTO> listadoIncidentesDTO = new ArrayList<>();

        if (listadoPruebas.isEmpty()) {
            throw new RuntimeException("No se encontraron pruebas con incidentes para generar el reporte.");
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n==============================================\n");
        reporte.append("Reporte de Incidentes\n");
        reporte.append("==============================================\n");

        listadoPruebas.forEach(prueba -> {
            ReporteIncidentesDTO pruebaDTO = new ReporteIncidentesDTO();

            //Id
            pruebaDTO.setPruebaId(prueba.getId());

            //Fecha
            pruebaDTO.setFechaInicio(prueba.getFechaHoraInicio().toString());
            if (prueba.getFechaHoraFin() != null) {
                pruebaDTO.setFechaFin(prueba.getFechaHoraFin().toString());
            }

            // Interesado
            pruebaDTO.setApellidoInteresado(prueba.getInteresado().getApellido());
            pruebaDTO.setNombreInteresado(prueba.getInteresado().getNombre());

            //Vehiculo
            pruebaDTO.setModeloVehiculo(prueba.getVehiculo().getModelo().getDescripcion());
            pruebaDTO.setMarcaVehiculo(prueba.getVehiculo().getModelo().getMarca().getNombre());
            pruebaDTO.setPatente(prueba.getVehiculo().getPatente());

            //Empleado
            pruebaDTO.setNombreEmpleado(prueba.getEmpleado().getNombre());
            pruebaDTO.setApellidoEmpleado(prueba.getEmpleado().getApellido());

            listadoIncidentesDTO.add(pruebaDTO);

            reporte.append(String.format("Prueba ID: %d\n", prueba.getId()));

            // Vehiculo
            reporte.append(String.format("Modelo vehículo: %s\n", prueba.getVehiculo().getModelo().getDescripcion()));
            reporte.append(String.format("Marca vehículo: %s\n", prueba.getVehiculo().getModelo().getMarca().getNombre()));
            reporte.append(String.format("Patente vehículo: %s\n", prueba.getVehiculo().getPatente()));

            // Interesado
            reporte.append(String.format("Nombre interesado: %s\n", prueba.getInteresado().getNombre()));
            reporte.append(String.format("Apellido interesado: %s\n", prueba.getInteresado().getApellido()));

            // Empleado
            reporte.append(String.format("Empleado: %s\n", prueba.getEmpleado().getNombre()));
            reporte.append(String.format("Empleado: %s\n", prueba.getEmpleado().getApellido()));


            // Fecha y hora de inicio y fin
            reporte.append(String.format("Fecha y Hora de Inicio: %s\n", prueba.getFechaHoraInicio().toString()));
            if (prueba.getFechaHoraFin() != null){
                reporte.append(String.format("Fecha y Hora de Fin: %s\n", prueba.getFechaHoraFin().toString()));
            }
            reporte.append("\n==============================================\n");
        });
        System.out.printf(reporte.toString());

        return listadoIncidentesDTO;
    }
}
