package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonIgnore // Ignorar este atributo al serializar a JSON (evitar bucle infinito)
    @OneToMany(mappedBy = "marca")
    private List<Modelo> modelos;
}