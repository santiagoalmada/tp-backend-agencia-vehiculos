package utn.frc.backend.tutor.tp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        // Crear una nueva notificación con los datos recibidos (DTO)
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(notificacionDTO.getTipo());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setTelefono(notificacionDTO.getTelefono());
        notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());
        notificacion.setNombreInteresado(notificacionDTO.getNombreInteresado());

        // Guardar la notificación en la base de datos
        notificacionRepository.save(notificacion);
    }

    public void nuevaPromocion(PromocionDTO promocion) {
        // Obtener la fecha y hora actuales y formatearlas
        LocalDateTime fechaHora = LocalDateTime.now();

        // Crear una notificacion para cada telefono en la lista de telefonos
        for (String telefono : promocion.getTelefonos()) {
            Notificacion notificacion = new Notificacion();
            notificacion.setTipo("PROMOCION");
            notificacion.setMensaje(promocion.getMensaje());
            notificacion.setTelefono(telefono);
            notificacion.setFechaEnvio(fechaHora);
            notificacionRepository.save(notificacion);
        }
    }
}
