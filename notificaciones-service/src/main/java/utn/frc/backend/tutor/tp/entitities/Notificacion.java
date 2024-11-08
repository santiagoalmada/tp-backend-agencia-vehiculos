package utn.frc.backend.tutor.tp.entitities;

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
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //todo
    private Long id;

    @Column(name="mensaje")
    private String mensaje;

    @Column(name="tipo")
    private String tipo;
    // Promocion -- Notificacion de area

    @Column(name="telefono")
    private String telefono;

    @Column(name="fecha_envio")
    private LocalDateTime fechaEnvio;

}
