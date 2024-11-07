package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PosicionDTO {
    private Long vehiculoId;
    private Double latitud;
    private Double longitud;

    public PosicionDTO(Long vehiculoId, LocalDateTime fechaHora, Double latitud, Double longitud) {
        this.vehiculoId = vehiculoId;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
