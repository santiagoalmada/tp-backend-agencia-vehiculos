package com.tpi.pruebas_manejo.pruebas_manejo_service.services;

import com.tpi.pruebas_manejo.pruebas_manejo_service.dtos.NotificacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacionService {

    private final RestTemplate restTemplate;

    @Value("${notificaciones.service.url}") // URL del microservicio de Notificaciones
    private String notificacionesServiceUrl;

    public NotificacionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarNotificacion(NotificacionDTO notificacionDTO) {
        // Definir la URL del endpoint en el microservicio de Notificaciones
        String url = notificacionesServiceUrl + "/nueva";

        try {
            // Imprimir la notificación a enviar
            System.out.println(notificacionDTO.toString());

            // Realizar el POST hacia el microservicio de Notificaciones
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    url, // URL del endpoint
                    notificacionDTO, // Objeto a enviar
                    Void.class // Tipo de respuesta esperada
            );

            // Verificar si la respuesta fue exitosa (HTTP 200 OK)
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error al enviar la notificación de tipo " + notificacionDTO.getTipo() + ": " + response.getStatusCode());
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Manejar errores de cliente (4xx) o servidor (5xx)
            throw new RuntimeException("Error al enviar la notificación de tipo " + notificacionDTO.getTipo() + ": " + e.getMessage(), e);
        } catch (Exception e) {
            // Manejar cualquier otro tipo de excepción
            throw new RuntimeException("Error inesperado al intentar enviar la notificación de tipo " + notificacionDTO.getTipo() + ": " + e.getMessage(), e);
        }
    }

}
