package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import com.tpi.pruebas_manejo.pruebas_manejo_service.utils.Coordenada;
import com.tpi.pruebas_manejo.pruebas_manejo_service.utils.Zona;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    // one to one con vehiculo
    @OneToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @Column(name = "FECHA_HORA")
    private LocalDateTime fechaHora;

    private Double latitud;
    private Double longitud;

    // Verificar si la posicion esta dentro del radio
    public boolean estaDentroDelRadio(Coordenada coordenadasCentro, double radio) {
        // Distancia euclidiana (pitagoras)
        double distancia = Math.sqrt(Math.pow(this.latitud - coordenadasCentro.getLat(), 2) + Math.pow(this.longitud - coordenadasCentro.getLon(), 2));
        return distancia <= radio;
    }

    // Verificar si la posicion esta dentro de algunas zonas
    public boolean estaDentroDeZonas(List<Zona> zonas) {
        for (Zona zona : zonas) {
            if (this.latitud >= zona.getNoroeste().getLat() && this.latitud <= zona.getSureste().getLat() &&
                    this.longitud >= zona.getNoroeste().getLon() && this.longitud <= zona.getSureste().getLon()) {
                return true;
            }
        }
        return false;
    }
}
