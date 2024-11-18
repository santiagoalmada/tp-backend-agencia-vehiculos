package utn.frc.backend.tutor.tp.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PromocionDTO {
    private String mensaje; // Mensaje de la notificación.

    // Lista de destinatarios a los que se le enviará la notificación, cada uno con teléfono y nombre.
    private List<DestinatarioDTO> destinatarios;
}
