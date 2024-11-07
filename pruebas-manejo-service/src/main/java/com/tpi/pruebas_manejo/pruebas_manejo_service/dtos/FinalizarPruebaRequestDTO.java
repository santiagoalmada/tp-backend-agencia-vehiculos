package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinalizarPruebaRequestDTO {
    private Long pruebaId;
    private String comentarios;

    public FinalizarPruebaRequestDTO(Long pruebaId, String comentarios) {
        this.pruebaId = pruebaId;
        this.comentarios = comentarios;
    }
}
