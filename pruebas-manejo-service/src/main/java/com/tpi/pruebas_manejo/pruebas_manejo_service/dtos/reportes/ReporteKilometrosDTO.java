package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ReporteKilometrosDTO {
    private Long vehiculoId;
    private String fechaInicio;
    private String fechaFin;
    // Atributos veheiculo
    private String patente;
    private double kmRecorridos;

    public ReporteKilometrosDTO(Long vehiculoId, String fechaInicio, String fechaFin, String patente, double kmRecorridos) {
        this.vehiculoId = vehiculoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.patente = patente;
        this.kmRecorridos = kmRecorridos;
    }
}
