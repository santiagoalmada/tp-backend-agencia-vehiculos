package com.tpi.pruebas_manejo.pruebas_manejo_service.controllers;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReportePruebasDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes.ReporteKilometrosDTO;
import com.tpi.pruebas_manejo.pruebas_manejo_service.services.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/pruebas/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    // Endpoint N6 - Reporte iii.
    @GetMapping("/kilometros")
    public ResponseEntity<?> generarReporteKilometraje(
            @RequestParam Long vehiculoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        try {
            // Validar que la fecha de inicio sea menor que la fecha de fin
            if (fechaInicio.isAfter(fechaFin)) {
                return ResponseEntity.badRequest().body("Error: La fecha de inicio no puede ser mayor que la fecha de fin.");
            }
            // Validar que la fecha de inicio no sea mayor a la fecha actual
            if (fechaInicio.isAfter(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("Error: La fecha de inicio no puede ser mayor a la fecha actual.");
            }

            ReporteKilometrosDTO reporte = reportesService.generarReporteKilometrosRecorridos(vehiculoId, fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al generar el reporte: " + e.getMessage());
        }

    }

    // Endpoint N6 - Reporte i. y ii.
    @GetMapping("/incidentes")
    public ResponseEntity<?> generarReporteIncidentes(
            @RequestParam(value = "legajoEmp", required = false) Long legajoEmp) {
        try {
            List<ReportePruebasDTO> reporte = reportesService.generarReporteIncidentes(legajoEmp);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al generar el reporte: " + e.getMessage());
        }
    }

    // Endpoint N6 - Reporte iv.
    @GetMapping("/pruebas-vehiculo")
    public ResponseEntity<?> generarReportePruebasPorVehiculo(
            @RequestParam Long vehiculoId) {
        try {
            List<ReportePruebasDTO> reporte = reportesService.generarReportePruebasPorVehiculo(vehiculoId);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al generar el reporte: " + e.getMessage());
        }
    }

}
