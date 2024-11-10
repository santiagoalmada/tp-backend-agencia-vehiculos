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

    private String mensaje;
    private String tipo;
    private String telefono;
    private LocalDateTime fechaEnvio;
    private String nombreInteresado;

    public NotificacionDTO(String mensaje, String tipo, String telefono, LocalDateTime fechaEnvio, String nombreInteresado) {
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.telefono = telefono;
        this.fechaEnvio = fechaEnvio;
        this.nombreInteresado = nombreInteresado;
    }
}
