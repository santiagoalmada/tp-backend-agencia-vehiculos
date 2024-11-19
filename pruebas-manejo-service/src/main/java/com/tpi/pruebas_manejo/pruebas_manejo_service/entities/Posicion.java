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
    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @Column(name = "FECHA_HORA")
    private LocalDateTime fechaHora;

    private Double latitud;
    private Double longitud;

    // Constante de conversión de grados a kilómetros
    private static final double KILOMETROS_POR_GRADO = 111.32;

    // Verificar si la posicion esta dentro del radio
    public boolean estaDentroDelRadio(Coordenada coordenadasCentro, double radio) {
        // Diferencia de latitud y longitud
        double diferenciaLatitud = this.latitud - coordenadasCentro.getLat();
        double diferenciaLongitud = this.longitud - coordenadasCentro.getLon();

        // Aplicar la fórmula de distancia euclidiana (pitagoras)
        double distancia = Math.sqrt(Math.pow(diferenciaLatitud * KILOMETROS_POR_GRADO, 2) +
                Math.pow(diferenciaLongitud * KILOMETROS_POR_GRADO, 2));

        // Verificar si la distancia está dentro del radio
        System.out.println("Distancia desde el centro a la posicion: " + distancia);
        return distancia <= radio;
    }

    // Verificar si la posición está dentro de alguna de las zonas recibidas
    public boolean estaDentroDeZonas(List<Zona> zonas) {
        for (Zona zona : zonas) {
            // Verificar si la latitud está dentro del rango de la zona
            boolean latitudDentro = this.latitud <= zona.getNoroeste().getLat() && this.latitud >= zona.getSureste().getLat();

            // Verificar si la longitud está dentro del rango de la zona
            boolean longitudDentro = this.longitud >= zona.getNoroeste().getLon() && this.longitud <= zona.getSureste().getLon();

            // Si ambas condiciones son verdaderas, la posición está dentro de la zona
            if (latitudDentro && longitudDentro) {
                System.out.println("La posición está dentro de una zona.");
                return true;
            }
        }
        return false;
    }

    // Calcular la distancia entre dos posiciones (en km) (distancia euclidiana)
    public double calcularDistancia(Posicion otraPosicion) {
        double distancia = Math.sqrt(Math.pow(this.latitud - otraPosicion.getLatitud(), 2) + Math.pow(this.longitud - otraPosicion.getLongitud(), 2));

        return distancia * KILOMETROS_POR_GRADO;
    };

}
