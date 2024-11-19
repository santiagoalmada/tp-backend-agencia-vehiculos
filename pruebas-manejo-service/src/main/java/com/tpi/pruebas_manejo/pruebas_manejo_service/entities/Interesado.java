package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "interesados")
public class Interesado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    private String documento;
    private String nombre;
    private String apellido;

    private boolean restringido; // True or False

    @Column(name = "NRO_LICENCIA")
    private String nroLicencia;

    @Column(name = "FECHA_VENCIMIENTO_LICENCIA")
    private LocalDateTime fechaVencimientoLicencia;

    @JsonIgnore // Ignorar este atributo al serializar a JSON (evitar bucle infinito)
    @OneToMany(mappedBy = "interesado")
    private List<Prueba> pruebas;

    // Métodos:
    public boolean tenesLicenciaVencida() {
        return fechaVencimientoLicencia.isBefore(LocalDateTime.now());
    }
}

