package utn.frc.backend.tutor.tp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.backend.tutor.tp.dtos.DestinatarioDTO;
import utn.frc.backend.tutor.tp.dtos.NotificacionDTO;
import utn.frc.backend.tutor.tp.dtos.PromocionDTO;
import utn.frc.backend.tutor.tp.entities.Notificacion;
import utn.frc.backend.tutor.tp.repositories.NotificacionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> getAllNotificaciones() {
        return notificacionRepository.findAll();
    }

    public void nuevaNotificacion(NotificacionDTO notificacionDTO) {
        LocalDateTime fechaHora = LocalDateTime.now();

        // Crear una nueva notificación con los datos recibidos (DTO)
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(notificacionDTO.getTipo());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setTelefono(notificacionDTO.getTelefono());
        notificacion.setFechaEnvio(fechaHora);
        notificacion.setNombreDestinatario(notificacionDTO.getNombreDestinatario());

        // Guardar la notificación en la base de datos
        notificacionRepository.save(notificacion);
    }

    public void nuevaPromocion(PromocionDTO promocion) {
        // Obtener la fecha y hora actuales
        LocalDateTime fechaHora = LocalDateTime.now();

        // Crear una notificación para cada destinatario en la lista de destinatarios
        for (DestinatarioDTO destinatario : promocion.getDestinatarios()) {
            Notificacion notificacion = new Notificacion();
            notificacion.setTipo("PROMOCION");
            notificacion.setMensaje(promocion.getMensaje());
            notificacion.setTelefono(destinatario.getTelefono()); // Obtener el teléfono del destinatario
            notificacion.setNombreDestinatario(destinatario.getNombreDestinatario()); // Obtener el nombre del destinatario
            notificacion.setFechaEnvio(fechaHora);

            notificacionRepository.save(notificacion);
        }
    }

}
