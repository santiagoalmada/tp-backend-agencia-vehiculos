package utn.frc.backend.tutor.tp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frc.backend.tutor.tp.dtos.NotificacionDTO;
import utn.frc.backend.tutor.tp.entitities.Notificacion;
import utn.frc.backend.tutor.tp.repositories.NotificacionRepository;

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
        notificacion.setId(notificacionDTO.getId());
        notificacion.setTipo(notificacionDTO.getTipo());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setTelefono(notificacionDTO.getTelefono());
        notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());

        // Guardar la notificación en la base de datos
        notificacionRepository.save(notificacion);
    }
}
