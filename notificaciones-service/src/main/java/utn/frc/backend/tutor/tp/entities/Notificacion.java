package utn.frc.backend.tutor.tp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="mensaje")
    private String mensaje;

    @Column(name="tipo")
    private String tipo; // Alerta, Promocion

    @Column(name="telefono")
    private String telefono;

    @Column(name="nombre_destinatario")
    private String nombreDestinatario;

    @Column(name="fecha_envio")
    private LocalDateTime fechaEnvio;

}
