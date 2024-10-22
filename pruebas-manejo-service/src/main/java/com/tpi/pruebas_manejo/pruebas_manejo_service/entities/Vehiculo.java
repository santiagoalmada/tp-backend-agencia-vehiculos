package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "ID_MODELO")
    private Modelo modelo;
}