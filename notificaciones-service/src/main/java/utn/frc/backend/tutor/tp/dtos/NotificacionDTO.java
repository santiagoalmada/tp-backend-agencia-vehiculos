package utn.frc.backend.tutor.tp.dtos;


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
    private String nombreDestinatario;

    public NotificacionDTO(String mensaje, String tipo, String telefono, LocalDateTime fechaEnvio, String nombreDestinatario) {
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.telefono = telefono;
        this.fechaEnvio = fechaEnvio;
        this.nombreDestinatario = nombreDestinatario;
    }
}
