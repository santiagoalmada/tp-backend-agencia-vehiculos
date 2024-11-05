package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "modelos")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany (mappedBy = "modelo")
    private List<Vehiculo> vehiculos;

    @ManyToOne
    @JoinColumn(name = "ID_MARCA")
    private Marca marca;

    private String descripcion;
}
