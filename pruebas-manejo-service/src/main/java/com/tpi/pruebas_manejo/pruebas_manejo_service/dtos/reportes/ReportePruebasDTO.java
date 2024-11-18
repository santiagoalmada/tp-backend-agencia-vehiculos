package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.reportes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportePruebasDTO {
    private Long pruebaId;
    private String fechaInicioPrueba;
    private String fechaFinPrueba;
    private String patente;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String nombreInteresado;
    private String apellidoInteresado;
    private String nombreEmpleado;
    private String apellidoEmpleado;

    public ReportePruebasDTO(Long pruebaId, String fechaInicioPrueba, String fechaFinPrueba, String patente, String marcaVehiculo, String modeloVehiculo, String nombreInteresado, String apellidoInteresado, String nombreEmpleado, String apellidoEmpleado) {
        this.pruebaId = pruebaId;
        this.fechaInicioPrueba = fechaInicioPrueba;
        this.fechaFinPrueba = fechaFinPrueba;
        this.patente = patente;
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
        this.nombreInteresado = nombreInteresado;
        this.apellidoInteresado = apellidoInteresado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
    }
}
