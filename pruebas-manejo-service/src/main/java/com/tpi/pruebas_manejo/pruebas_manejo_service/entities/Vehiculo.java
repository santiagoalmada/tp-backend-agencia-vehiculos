package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;

    private int anio;

    //one to one con posicion
    @JsonIgnore // Ignorar este atributo al serializar a JSON (evitar bucle infinito)
    @OneToMany(mappedBy = "vehiculo")
    private List<Posicion> posiciones;


    @JsonIgnore // Ignorar este atributo al serializar a JSON (evitar bucle infinito)
    @OneToMany(mappedBy = "vehiculo")
    private List<Prueba> pruebas;


    @ManyToOne
    @JoinColumn(name = "ID_MODELO")
    private Modelo modelo;

    // Métodos:
    public boolean estasSiendoProbado() {
        // Esta siendo probado si alguna de sus pruebas esta en curso (no tiene fechaHoraFin)
        for (Prueba prueba : pruebas) {
            if (prueba.estasEnCurso()) {
                return true;
            }
        }
        return false;
    }

    public Prueba obtenerPruebaEnCurso() {
        // Devuelve la prueba que esta en curso
        for (Prueba prueba : pruebas) {
            if (prueba.estasEnCurso()) {
                return prueba;
            }
        }
        return null;
    }
}