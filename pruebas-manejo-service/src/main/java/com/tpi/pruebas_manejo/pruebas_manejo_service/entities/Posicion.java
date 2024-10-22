package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "posiciones")
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @Column(name = "FECHA_HORA")
    private LocalDateTime fechaHora;

    private Double latitud;
    private Double longitud;
}
