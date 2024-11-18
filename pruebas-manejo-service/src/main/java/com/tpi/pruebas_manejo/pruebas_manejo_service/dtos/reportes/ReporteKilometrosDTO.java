package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ReporteKilometrosDTO {
    private Long vehiculoId;
    private String fechaDesdeReporte;
    private String fechaHastaReporte;
    // Atributos vehiculo
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String patente;
    private double kmRecorridos;

    public ReporteKilometrosDTO(Long vehiculoId, String fechaDesdeReporte, String fechaHastaReporte, String marcaVehiculo, String modeloVehiculo, String patente, double kmRecorridos) {
        this.vehiculoId = vehiculoId;
        this.fechaDesdeReporte = fechaDesdeReporte;
        this.fechaHastaReporte = fechaHastaReporte;
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
        this.patente = patente;
        this.kmRecorridos = kmRecorridos;
    }
}
