package utn.frc.backend.tutor.tp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.backend.tutor.tp.dtos.NotificacionDTO;
import utn.frc.backend.tutor.tp.dtos.PromocionDTO;
import utn.frc.backend.tutor.tp.entities.Notificacion;
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
    public ResponseEntity<String> nuevaNotificacion(@RequestBody NotificacionDTO notificacion) {
        try {
            notificacionService.nuevaNotificacion(notificacion);
            return ResponseEntity.ok("Notificación creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // POST /notificaciones/nueva-promocion
    @PostMapping("/nueva-promocion")
    public ResponseEntity<String> nuevaPromocion(@RequestBody PromocionDTO promocion) {
        try {
            notificacionService.nuevaPromocion(promocion);
            return ResponseEntity.ok("Promoción creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}









