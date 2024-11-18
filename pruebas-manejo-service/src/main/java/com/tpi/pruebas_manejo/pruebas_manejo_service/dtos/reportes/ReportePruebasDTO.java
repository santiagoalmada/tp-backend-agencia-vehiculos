package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportePruebasDTO {
    private Long pruebaId;
    private String fechaDesdeReporte;
    private String fechaHastaReporte;
    private String patente;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String nombreInteresado;
    private String apellidoInteresado;
    private String nombreEmpleado;
    private String apellidoEmpleado;

    public ReportePruebasDTO(Long pruebaId, String fechaDesdeReporte, String fechaHastaReporte, String modeloVehiculo, String marcaVehiculo, String patente, String nombreInteresado, String apellidoInteresado, String nombreEmpleado, String apellidoEmpleado) {
        this.pruebaId = pruebaId;
        this.fechaDesdeReporte = fechaDesdeReporte;
        this.fechaHastaReporte = fechaHastaReporte;
        this.modeloVehiculo = modeloVehiculo;
        this.marcaVehiculo = marcaVehiculo;
        this.patente = patente;
        this.nombreInteresado = nombreInteresado;
        this.apellidoInteresado = apellidoInteresado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
    }
}
