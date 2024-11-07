package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearPruebaRequestDTO {
    private Long interesadoId;  // ID del interesado (cliente)
    private Long vehiculoId;    // ID del vehículo
    private Long empleadoId;    // ID del empleado que realiza la prueba

    // Constructor con todos los atributos
    public void CrearPruebaRequest(Long interesadoId, Long vehiculoId, Long empleadoId, String comentarios) {
        this.interesadoId = interesadoId;
        this.vehiculoId = vehiculoId;
        this.empleadoId = empleadoId;
    }
}
