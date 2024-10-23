package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Empleado;

@Data
@NoArgsConstructor
public class EmpleadoDTO {
    private Long legajo;
    private String nombre;
    private String apellido;
    private String telefonoContacto;

    public EmpleadoDTO(Empleado empleado) {
        this.legajo = empleado.getLegajo();
        this.nombre = empleado.getNombre();
        this.apellido = empleado.getApellido();
        this.telefonoContacto = empleado.getTelefonoContacto();
    }

    public Empleado toEntity() {
        Empleado empleado = new Empleado();
        empleado.setLegajo(this.legajo);
        empleado.setNombre(this.nombre);
        empleado.setApellido(this.apellido);
        empleado.setTelefonoContacto(this.telefonoContacto);
        return empleado;
    }
}
