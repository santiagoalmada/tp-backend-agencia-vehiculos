package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotificacionDTO {

    private int id; //todo
    private String mensaje;
    private String tipo;
    private String telefono;
    private LocalDateTime fechaEnvio;

    public NotificacionDTO(String mensaje, String tipo, String telefono, LocalDateTime fechaEnvio) {
        this.id = id;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.telefono = telefono;
        this.fechaEnvio = fechaEnvio;
    }
}
