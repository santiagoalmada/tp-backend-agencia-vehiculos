package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "FECHA_HORA_INICIO")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN")
    private LocalDateTime fechaHoraFin;

    private String comentarios;
}

