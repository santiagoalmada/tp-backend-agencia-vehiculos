package com.tpi.pruebas_manejo.pruebas_manejo_service.controllers;

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

@RestController
@RequestMapping("api/pruebas/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    @GetMapping("/kilometros")
    public ResponseEntity<?> generarReporteKilometraje(
            @RequestParam Long vehiculoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        try {
            ReporteKilometrosDTO reporte = reportesService.generarReporteKilometrosRecorridos(vehiculoId, fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al generar el reporte: " + e.getMessage());
        }

    }

}
