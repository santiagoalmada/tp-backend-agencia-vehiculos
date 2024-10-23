package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Marca;

@Data
@NoArgsConstructor
public class MarcaDTO {
    private Long id;
    private String nombre;

    public MarcaDTO(Marca marca) {
        this.id = marca.getId();
        this.nombre = marca.getNombre();
    }

    public Marca toEntity() {
        Marca marca = new Marca();
        marca.setId(this.id);
        marca.setNombre(this.nombre);
        return marca;
    }
}
