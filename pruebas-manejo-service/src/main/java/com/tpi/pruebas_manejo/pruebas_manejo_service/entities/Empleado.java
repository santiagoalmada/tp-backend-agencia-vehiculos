package com.tpi.pruebas_manejo.pruebas_manejo_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @Column(name = "LEGAJO")
    private Long legajo;

    private String nombre;
    private String apellido;

    @Column(name = "TELEFONO_CONTACTO")
    private String telefonoContacto;

    @OneToMany (mappedBy = "empleado")
    private List<Prueba> pruebas;

}
