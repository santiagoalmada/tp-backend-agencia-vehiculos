package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Prueba;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PruebaDTO {
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String comentarios;

    // Relación con otras entidades
    private VehiculoDTO vehiculo;
    private InteresadoDTO interesado;
    private EmpleadoDTO empleado;

    // Constructor para convertir desde la entidad `Prueba` a DTO
    public PruebaDTO(Prueba prueba) {
        this.id = prueba.getId();
        this.fechaHoraInicio = prueba.getFechaHoraInicio();
        this.fechaHoraFin = prueba.getFechaHoraFin();
        this.comentarios = prueba.getComentarios();
        this.vehiculo = new VehiculoDTO(prueba.getVehiculo());
        this.interesado = new InteresadoDTO(prueba.getInteresado());
        this.empleado = new EmpleadoDTO(prueba.getEmpleado());
    }

    // Metodo para convertir de DTO a entidad `Prueba`
    public Prueba toEntity() {
        Prueba prueba = new Prueba();
        prueba.setId(this.id);
        prueba.setFechaHoraInicio(this.fechaHoraInicio);
        prueba.setFechaHoraFin(this.fechaHoraFin);
        prueba.setComentarios(this.comentarios);
        prueba.setVehiculo(this.vehiculo.toEntity());
        prueba.setInteresado(this.interesado.toEntity());
        prueba.setEmpleado(this.empleado.toEntity());
        return prueba;
    }
}
