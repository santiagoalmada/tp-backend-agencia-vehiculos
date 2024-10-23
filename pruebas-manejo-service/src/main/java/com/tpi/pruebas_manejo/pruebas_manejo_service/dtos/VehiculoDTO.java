package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Vehiculo;

@Data
@NoArgsConstructor
public class VehiculoDTO {
    private Long id;
    private String patente;
    private ModeloDTO modelo;

    public VehiculoDTO(Vehiculo vehiculo) {
        this.id = vehiculo.getId();
        this.patente = vehiculo.getPatente();
        this.modelo = new ModeloDTO(vehiculo.getModelo());
    }

    public Vehiculo toEntity() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(this.id);
        vehiculo.setPatente(this.patente);
        vehiculo.setModelo(this.modelo.toEntity());
        return vehiculo;
    }
}
