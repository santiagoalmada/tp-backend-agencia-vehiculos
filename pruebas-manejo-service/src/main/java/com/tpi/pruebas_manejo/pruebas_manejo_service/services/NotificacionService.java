package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.NotificacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacionService {

    private final RestTemplate restTemplate;

    @Value("${notificaciones.service.url}")
    private String notificacionesServiceUrl;

    public NotificacionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarNotificacion(NotificacionDTO notificacionDTO) {
        // Definir la URL del endpoint en el microservicio de Notificaciones
        String url = notificacionesServiceUrl + "/nueva";

        // Realizar el POST hacia el microservicio de Notificaciones
        restTemplate.postForObject(
                url, // URL del endpoint
                notificacionDTO, // Objeto a enviar
                Void.class); // Tipo de respuesta esperada (Void) // TODO: Cambiar a ResponseEntity<Void> --> para manejar errores


    }
}
