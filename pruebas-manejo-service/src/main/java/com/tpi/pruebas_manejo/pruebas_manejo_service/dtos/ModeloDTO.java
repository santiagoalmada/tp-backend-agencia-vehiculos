package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Modelo;

@Data
@NoArgsConstructor
public class ModeloDTO {
    private Long id;
    private String descripcion;
    private MarcaDTO marca;

    public ModeloDTO(Modelo modelo) {
        this.id = modelo.getId();
        this.descripcion = modelo.getDescripcion();
        this.marca = new MarcaDTO(modelo.getMarca());
    }

    public Modelo toEntity() {
        Modelo modelo = new Modelo();
        modelo.setId(this.id);
        modelo.setDescripcion(this.descripcion);
        modelo.setMarca(this.marca.toEntity());
        return modelo;
    }
}
