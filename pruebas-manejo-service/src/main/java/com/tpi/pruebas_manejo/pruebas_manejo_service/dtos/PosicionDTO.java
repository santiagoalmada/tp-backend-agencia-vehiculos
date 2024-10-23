package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Posicion;

@Data
@NoArgsConstructor
public class PosicionDTO {
    private Long id;
    private Double latitud;
    private Double longitud;

    public PosicionDTO(Posicion posicion) {
        this.id = posicion.getId();
        this.latitud = posicion.getLatitud();
        this.longitud = posicion.getLongitud();
    }

    public Posicion toEntity() {
        Posicion posicion = new Posicion();
        posicion.setId(this.id);
        posicion.setLatitud(this.latitud);
        posicion.setLongitud(this.longitud);
        return posicion;
    }
}
