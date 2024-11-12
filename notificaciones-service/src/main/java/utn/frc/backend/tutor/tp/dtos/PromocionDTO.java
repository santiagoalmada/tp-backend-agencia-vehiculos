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
    private List<String> telefonos; // Lista de teléfonos a los que se enviará la notificación.
}
