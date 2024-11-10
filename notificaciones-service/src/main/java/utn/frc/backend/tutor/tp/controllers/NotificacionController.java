package utn.frc.backend.tutor.tp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.frc.backend.tutor.tp.dtos.NotificacionDTO;
import utn.frc.backend.tutor.tp.entitities.Notificacion;
import utn.frc.backend.tutor.tp.services.NotificacionService;

import java.util.List;

@RestController
@RequestMapping("api/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    // GET /notificaciones
    @GetMapping
    public List<Notificacion> getAllNotificaciones() {
        return notificacionService.getAllNotificaciones();
    }

    // POST /notificaciones/nueva
    @PostMapping("/nueva")
    public void nuevaNotificacion(@RequestBody NotificacionDTO notificacion) {
        notificacionService.nuevaNotificacion(notificacion);
    }
}









