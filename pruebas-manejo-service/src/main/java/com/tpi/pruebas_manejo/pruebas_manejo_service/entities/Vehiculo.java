package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;

    private int anio;

    @OneToMany(mappedBy = "vehiculo")
    private List<Posicion> posiciones;

    @OneToMany(mappedBy = "vehiculo")
    private List<Prueba> pruebas;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO")
    private Modelo modelo;
}