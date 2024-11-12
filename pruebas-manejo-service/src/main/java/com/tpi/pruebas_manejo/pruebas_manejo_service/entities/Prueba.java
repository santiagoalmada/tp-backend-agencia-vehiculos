package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pruebas")
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "ID_INTERESADO")
    private Interesado interesado;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado empleado;

    // Fecha y hora de la prueba (timestamp en la BD)
    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    // Fecha hora fin de la prueba
    @Column(name = "FECHA_HORA_FIN")
    private LocalDateTime fechaHoraFin;

    @Column(name="excedio_limite")
    private boolean excedioLimite;

    private String comentarios;

    public boolean estasEnCurso() {
        return fechaHoraFin == null;
    }

    public boolean estasFinalizada() {
        return fechaHoraFin != null;
    }
}

