package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private boolean restringido;

    @Column(name = "NRO_LICENCIA")
    private String nroLicencia;

    @Column(name = "FECHA_VENCIMIENTO_LICENCIA")
    private LocalDate fechaVencimientoLicencia;

    @OneToMany(mappedBy = "interesado")
    private List<Prueba> pruebas;
}

