package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReportePruebasDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReporteKilometrosDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;
import com.tpi.pruebas_manejo.pruebas_manejo_service.repositories.EmpleadoRepository;
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
    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Reporte iii.
    public ReporteKilometrosDTO generarReporteKilometrosRecorridos(Long vehiculoId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Obtener el vehiculo por su ID
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).
                orElseThrow(() -> new RuntimeException("Vehículo no encontrado con el Id Ingresado."));

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

        // Devolver un DTO con la información necesaria
        return new ReporteKilometrosDTO(
                vehiculoId,
                fechaInicio.toString(),
                fechaFin.toString(),
                vehiculo.getPatente(),
                vehiculo.getModelo().getDescripcion(),
                vehiculo.getModelo().getMarca().getNombre(),
                distanciaRecorrida);
    }

    // Reporte i. y ii.
    public List<ReportePruebasDTO> generarReporteIncidentes (Long legajoEmp) {
        List <Prueba> listadoPruebas;
        // Si no se ingresa un legajo, se buscan todas las pruebas con incidentes
        if (legajoEmp == null) {
            listadoPruebas = pruebaRepository.findByExcedioLimiteIsTrue();
        // Si se ingresa un legajo, se buscan las pruebas con incidentes del empleado
        } else {
            // Validamos que el empleado ingresado exista
            empleadoRepository.findById(legajoEmp).
                    orElseThrow(() -> new RuntimeException("Empleado no encontrado con el legajo ingresado."));

            listadoPruebas = pruebaRepository.findByExcedioLimiteIsTrueAndEmpleado_Legajo(legajoEmp);
        }

        if (listadoPruebas.isEmpty()) {
            throw new RuntimeException("No se encontraron pruebas con incidentes para generar el reporte.");
        }

        return generarReportePruebas(listadoPruebas);
    }

    // Reporte iv.
    public List<ReportePruebasDTO> generarReportePruebasPorVehiculo (Long vehiculoId) {
        // Validamos que el vehiculo ingresado exista
        vehiculoRepository.findById(vehiculoId).
                orElseThrow(() -> new RuntimeException("Vehículo no encontrado con el Id Ingresado."));

        List<Prueba> listadoPruebas = pruebaRepository.findByVehiculo_Id(vehiculoId);

        if (listadoPruebas.isEmpty()) {
            throw new RuntimeException("No se encontraron pruebas con incidentes para generar el reporte.");
        }

        return generarReportePruebas(listadoPruebas);
    }


    // Funcion para generar reporte de pruebas, recibe como parametro una lista de pruebas
    private List<ReportePruebasDTO> generarReportePruebas(List<Prueba> listadoPruebas) {
        List<ReportePruebasDTO> listadoPruebasDTO = new ArrayList<>();

        // Iteramos sobre la lista de pruebas y generamos un DTO por cada una
        listadoPruebas.forEach(prueba -> {
            ReportePruebasDTO pruebaDTO = new ReportePruebasDTO();

            //Id
            pruebaDTO.setPruebaId(prueba.getId());

            //Fecha
            pruebaDTO.setFechaDesdeReporte(prueba.getFechaHoraInicio().toString());
            if (prueba.getFechaHoraFin() != null) {
                pruebaDTO.setFechaHastaReporte(prueba.getFechaHoraFin().toString());
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

            listadoPruebasDTO.add(pruebaDTO);
        });

        // Devolvemos la lista de DTOs
        return listadoPruebasDTO;
    }

}
