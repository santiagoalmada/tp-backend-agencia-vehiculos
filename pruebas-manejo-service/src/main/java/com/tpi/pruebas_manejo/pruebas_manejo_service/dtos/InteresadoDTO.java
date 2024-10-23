package com.tpi.pruebas_manejo.pruebas_manejo_service.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.tpi.pruebas_manejo.pruebas_manejo_service.entities.Interesado;
import lombok.Setter;

@Data
@NoArgsConstructor
public class InteresadoDTO {
    private Long id;
    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private boolean restringido;

    public InteresadoDTO(Interesado interesado) {
        this.id = interesado.getId();
        this.tipoDocumento = interesado.getTipoDocumento();
        this.documento = interesado.getDocumento();
        this.nombre = interesado.getNombre();
        this.apellido = interesado.getApellido();
        this.restringido = interesado.isRestringido();
    }

    public Interesado toEntity() {
        Interesado interesado = new Interesado();
        interesado.setId(this.id);
        interesado.setTipoDocumento(this.tipoDocumento);
        interesado.setDocumento(this.documento);
        interesado.setNombre(this.nombre);
        interesado.setApellido(this.apellido);
        interesado.setRestringido(this.restringido);
        return interesado;
    }
}
